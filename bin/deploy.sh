#!/bin/bash
#set -x

## This script will deploy and start the wcrs-address application.
## Please refer to README.deploy for futher details.

function env_alert() {
    echo "Environment variable $1 is not set."
    echo "Refer to the README.deploy file for more details."
    echo "Please set the required environment variable and try again. Exiting now."
    echo ""
    exit 2
}

echo ""

## Ensure required environment variables have been set.
if [[ -z "${WCRS_ADDRESS_JAVA_HOME}" ]]; then env_alert WCRS_ADDRESS_JAVA_HOME; fi
if [[ -z "${WCRS_ADDRESS_HOME}" ]]; then env_alert WCRS_ADDRESS_HOME; fi
if [[ -z "${WCRS_ADDRESS_SOURCE}" ]]; then env_alert WCRS_ADDRESS_SOURCE; fi
if [[ -z "${WCRS_ADDRESS_PORT}" ]]; then env_alert WCRS_ADDRESS_PORT; fi
if [[ -z "${WCRS_ADDRESS_ADMIN_PORT}" ]]; then env_alert WCRS_ADDRESS_ADMIN_PORT; fi
if [[ -z "${WCRS_ADDRESS_USER}" ]]; then env_alert WCRS_ADDRESS_USER; fi
if [[ -z "${WCRS_ADDRESS_PASSWORD}" ]]; then env_alert WCRS_ADDRESS_PASSWORD; fi

## Stop previously running wcrs-address.
echo "Stopping old wcrs-address."
if [ -f "${WCRS_ADDRESS_HOME}/live/logs/pid" ]; then
  WCRS_ADDRESS_PID=`cat "${WCRS_ADDRESS_HOME}/live/logs/pid"`
  kill -0 ${WCRS_ADDRESS_PID} > /dev/null 2>&1
  if [ $? -eq 0 ]; then
    kill ${WCRS_ADDRESS_PID} > /dev/null 2>&1
  fi
  rm "${WCRS_ADDRESS_HOME}/live/logs/pid"
fi
## A second check in case the pid file was out of date.
WCRS_ADDRESS_PID=`ps -ef | grep java | grep ${WCRS_ADDRESS_PORT} | awk '{print $2}'`
if [[ ! -z "${WCRS_ADDRESS_PID}" ]]; then
  kill ${WCRS_ADDRESS_PID} 
fi

## Create a new release directory.
if [ -f ${WCRS_ADDRESS_SOURCE}/jenkins_build_number ]; then
  JENKINS_BUILD_NUMBER=`cat ${WCRS_ADDRESS_SOURCE}/jenkins_build_number`
else
  JENKINS_BUILD_NUMBER="j"
fi
DATESTAMP=`date +%Y.%m.%d-%H.%M`
RELEASE_DIR="wcrs-address-${JENKINS_BUILD_NUMBER}-${DATESTAMP}"
echo "Creating new release directory ${RELEASE_DIR}"
mkdir "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}"
cd "${WCRS_ADDRESS_HOME}"
if [ -d "${WCRS_ADDRESS_HOME}/live" ]; then
  rm live
fi
ln -s "${RELEASE_DIR}" live

## Create sub-directories to deploy into.
if [ -d "${WCRS_ADDRESS_HOME}" ]; then
  if [ -w "${WCRS_ADDRESS_HOME}" ]; then
    for DIR in bin conf logs webapps; do
      if [ ! -d "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/${DIR}" ]; then
        echo "Creating directory: ${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/${DIR}" 
        mkdir "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/${DIR}" 
      fi
    done
  else
    echo "ERROR: Unable to write to ${WCRS_ADDRESS_HOME}. Exiting now."
    exit 1
  fi
else
  echo "ERROR: ${WCRS_ADDRESS_HOME} does not exist."
  exit 1
fi

## Deploy the bin scripts.
if [ ! -d "${WCRS_ADDRESS_SOURCE}/bin" ]; then
  echo "ERROR: Unable to locate ${WCRS_ADDRESS_SOURCE}/bin"
  echo "       Exiting now."
  echo ""
  exit 1
fi
cp "${WCRS_ADDRESS_SOURCE}/bin/README.deploy" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/"
cp "${WCRS_ADDRESS_SOURCE}/bin/stop.sh" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/stop.sh"
chmod 744 "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/stop.sh"
cp "${WCRS_ADDRESS_SOURCE}/bin/start.sh" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/start.sh"
chmod 744 "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/start.sh"
cp "${WCRS_ADDRESS_SOURCE}/bin/deploy.sh" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/deploy.sh"
chmod 744 "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/bin/deploy.sh"

## Deploy the most recent jar file.
WCRS_ADDRESS_JAR=`ls ${WCRS_ADDRESS_SOURCE}/target/waste-exemplar-address-lookup-*.jar | sort | tail -1`
WCRS_ADDRESS_JAR=$(basename ${WCRS_ADDRESS_JAR})
if [[ -z "${WCRS_ADDRESS_JAR}" ]]; then
  echo "ERROR: Unable to locate waste-exemplar-address-lookup jar file in ${WCRS_ADDRESS_SOURCE}/target"
  echo "       Exiting now."
  echo ""
  exit 1
fi
echo "Copying ${WCRS_ADDRESS_JAR} to ${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/webapps/"
cp "${WCRS_ADDRESS_SOURCE}/target/${WCRS_ADDRESS_JAR}" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/webapps/"

## Deploy the configuration file and set environment variables.
if [[ ! -f "${WCRS_ADDRESS_SOURCE}/configuration.yml" ]]; then
  echo "ERROR: Unable to locate ${WCRS_ADDRESS_SOURCE}/configuration.yml"
  echo "       Exiting now."
  echo ""
  exit 1
fi
## Keep a copy of the original config, before variable names have been changed.
cp "${WCRS_ADDRESS_SOURCE}/configuration.yml" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/configuration.yml.orig"
cp "${WCRS_ADDRESS_SOURCE}/configuration.yml" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/"
chmod 600 "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/configuration.yml"
echo "Setting environment variables in ${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/configuration.yml"
sed -i "s/WCRS_ADDRESS_USER/${WCRS_ADDRESS_USER}/g" \
       "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/configuration.yml"
sed -i "s/WCRS_ADDRESS_PASSWORD/${WCRS_ADDRESS_PASSWORD}/g" \
       "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/configuration.yml"

## Preserve the jenkins build number file.
if [ -f ${WCRS_ADDRESS_SOURCE}/jenkins_build_number ]; then
  cp "${WCRS_ADDRESS_SOURCE}/jenkins_build_number" "${WCRS_ADDRESS_HOME}/${RELEASE_DIR}/conf/"
fi

## Create live symlink.
echo "Creating symlink: ${WCRS_ADDRESS_HOME}/live"
cd "${WCRS_ADDRESS_HOME}"
if [ -d "${WCRS_ADDRESS_HOME}/live" ]; then
  rm live
fi
ln -s "${RELEASE_DIR}" live

## Start wcrs-address.
echo "Starting wcrs-address on port ${WCRS_ADDRESS_PORT}."
cd "${WCRS_ADDRESS_HOME}/live/logs"
if [ -f "${WCRS_ADDRESS_HOME}/live/logs/wcrs-address.log" ]; then
  mv wcrs-address.log wcrs-address.log.${DATESTAMP}
fi
nohup "${WCRS_ADDRESS_JAVA_HOME}/bin/java" -Ddw.http.port=${WCRS_ADDRESS_PORT} -Ddw.http.adminPort=${WCRS_ADDRESS_PORT} \
      -jar "${WCRS_ADDRESS_HOME}/live/webapps/${WCRS_ADDRESS_JAR}" \
      server "${WCRS_ADDRESS_HOME}/live/conf/configuration.yml" > "${WCRS_ADDRESS_HOME}/live/logs/wcrs-address.log" &
echo $! > "${WCRS_ADDRESS_HOME}/live/logs/pid"

echo "Deploy complete."
echo ""
exit 0

