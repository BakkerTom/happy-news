FROM java:8-jre
EXPOSE 8082 9082

ADD build/libs/web-*.jar /web.jar

ENTRYPOINT ["java", "-jar", "web.jar"]