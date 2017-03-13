FROM java:8-jre
EXPOSE 8081 9081

ADD build/libs/crawler-*.jar /crawler.jar

ENTRYPOINT ["java", "-jar", "crawler.jar"]