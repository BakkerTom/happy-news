FROM java:8-jre
EXPOSE 8080 9080

RUN mkdir -p /opt/api/
WORKDIR /opt/api/
ADD build/libs/api-*.jar api.jar

ENTRYPOINT ["java", "-jar", "api.jar"]
