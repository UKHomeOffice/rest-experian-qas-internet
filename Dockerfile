FROM quay.io/ukhomeofficedigital/openjdk8:v1.1.0

COPY target/rest-experian-qas-internet-*[1-9]*.jar /app/rest-experian-qas-internet.jar

ENV CONFIG_FILE=/app/config.yaml

COPY configuration_yaml.example ${CONFIG_FILE}

ENTRYPOINT java -jar /app/rest-experian-qas-internet*.jar server ${CONFIG_FILE}
