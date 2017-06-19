FROM java:8-jre
EXPOSE 8081 9081

RUN mkdir -p /opt/crawler/
WORKDIR /opt/crawler/
ADD build/libs/crawler-*.jar crawler.jar

ENTRYPOINT ["java", "-jar", "crawler.jar"]
