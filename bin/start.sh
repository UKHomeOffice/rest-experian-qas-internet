#!/bin/bash
#set -x

## This script will start the wcrs-address application.
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
if [[ -z "${WCRS_ADDRESS_SOURCE}" ]]; then env_alert WCRS_ADDRESS_HOME; fi
if [[ -z "${WCRS_ADDRESS_PORT}" ]]; then env_alert WCRS_ADDRESS_PORT; fi
if [[ -z "${WCRS_ADDRESS_ADMIN_PORT}" ]]; then env_alert WCRS_ADDRESS_PORT; fi

## Ensure directory structure is in place.
if [ -d "${WCRS_ADDRESS_HOME}" ]; then
  if [ ! -w "${WCRS_ADDRESS_HOME}/live/logs" ]; then
    echo "ERROR: Unable to write to ${WCRS_ADDRESS_HOME}/live/logs. Exiting now."
    exit 1
  fi
else
  echo "ERROR: ${WCRS_ADDRESS_HOME} does not exist."
  exit 1
fi

## Verify that wcrs-address isn't currently running.
WCRS_ADDRESS_PID=`ps -ef | grep java | grep ${WCRS_ADDRESS_PORT} | awk '{print $2}'`
if [[ ! -z "${WCRS_ADDRESS_PID}" ]]; then
  echo "wcrs-address is already running or some other daemon is already using port ${WCRS_ADDRESS_PORT}."
  echo ""
  exit 0
fi

## Use the jar file with the highest version number.
WCRS_ADDRESS_JAR=`ls -tr ${WCRS_ADDRESS_HOME}/live/webapps/waste-exemplar-address-lookup-*.jar | sort | tail -1`

## Start wcrs-address.
echo "Starting wcrs-address on port ${WCRS_ADDRESS_PORT}."
cd "${WCRS_ADDRESS_HOME}/live/logs"
if [ -f "${WCRS_ADDRESS_HOME}/live/logs/nohup.out" ]; then
  DATESTAMP=`date +%Y.%m.%d-%H.%M`
  mv nohup.out nohup.out.${DATESTAMP}
fi
nohup "${WCRS_ADDRESS_JAVA_HOME}/bin/java" -Ddw.http.port=${WCRS_ADDRESS_PORT} \
      -Ddw.http.adminPort=${WCRS_ADDRESS_PORT} \
      -jar "${WCRS_ADDRESS_JAR}" \
      server "${WCRS_ADDRESS_HOME}/live/conf/configuration.yml" > "${WCRS_ADDRESS_HOME}/live/logs/wcrs-address.log" &
echo $! > "${WCRS_ADDRESS_HOME}/live/logs/pid"

echo ""
exit 0

