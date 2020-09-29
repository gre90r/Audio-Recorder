FROM maven:3.6.3-jdk-11

########################
# install dependencies #
########################
RUN sed 's/main$/main universe/' -i /etc/apt/sources.list \
    && apt-get update \
    && apt-get install -y software-properties-common \
    && apt-get install -y libxext-dev libxrender-dev libxtst-dev \
    && apt-get install -y x11vnc xvfb

RUN mkdir ~/.vnc
RUN x11vnc -storepasswd 1234 ~/.vnc/passwd

###########################
## install Audio Recorder #
###########################
COPY ./src /home/app/src
COPY ./pom.xml /home/app
RUN mvn -f /home/app/pom.xml install -Dmaven.test.skip=true
RUN mvn -f /home/app/pom.xml clean package assembly:single -Dtest=!AudioRecorderTest

ENTRYPOINT ["java", "-jar", "/home/app/target/Dream-Audio-Recorder-1.0-SNAPSHOT-jar-with-dependencies.jar"]
