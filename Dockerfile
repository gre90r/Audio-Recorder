FROM openjdk:11
FROM maven:3.6.3-jdk-11 AS build
COPY ./src /home/app/src
COPY ./pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package assembly:single -Dmaven.test.skip=true

ENTRYPOINT ["java", "-jar", "/home/app/target/Dream-Audio-Recorder-1.0-SNAPSHOT-jar-with-dependencies.jar"]