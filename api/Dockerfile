FROM java:8-jre
EXPOSE 8080 9080

ADD build/libs/api-*.jar /api.jar

ENTRYPOINT ["java", "-jar", "api.jar"]