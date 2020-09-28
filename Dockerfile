FROM ubuntu:14.04

RUN sed 's/main$/main universe/' -i /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y software-properties-common
RUN apt-get install -y sudo
RUN apt-get install -y libxext-dev libxrender-dev libxtst-dev
RUN apt-get install -y firefox

########################
# install dependencies #
########################
#RUN sed 's/main$/main universe/' -i /etc/apt/sources.list && \
#    apt-get update && apt-get install -y software-properties-common && \
#    add-apt-repository ppa:webupd8team/java -y && \
#    apt-get update && \
#    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
#    apt-get install -y oracle-java8-installer libxext-dev libxrender-dev libxtst-dev && \
#    apt-get clean && \
#    rm -rf /var/lib/apt/lists/* && \
#    rm -rf /tmp/*

#################
# install maven #
#################
#RUN wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz && \
#    tar xzvf apache-maven-3.6.3-bin.tar.gz && \
#    export PATH=apache-maven-3.6.3/bin:$PATH

##################################################################################
# enable use of local display for GUI application
# reference: http://fabiorehm.com/blog/2014/09/11/running-gui-apps-with-docker/
# reference: https://github.com/fgrehm/docker-netbeans/blob/master/Dockerfile
##################################################################################
RUN mkdir -p /home/developer && \
    echo "developer:x:1000:1000:Developer,,,:/home/developer:/bin/bash" >> /etc/passwd && \
    echo "developer:x:1000:" >> /etc/group && \
    echo "developer ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/developer && \
    chmod 0440 /etc/sudoers.d/developer && \
    chown developer:developer -R /home/developer

USER developer
ENV HOME /home/developer

########################
## install X11 DISPLAY #
########################
#ENV DISPLAY :0
## Install vnc, xvfb in order to create a 'fake' display and firefox
#RUN     apt-get update
#RUN     apt-get -y x11vnc xvfb firefox
#RUN     mkdir ~/.vnc
## Setup a password
#RUN     x11vnc -storepasswd 1234 ~/.vnc/passwd
## Autostart firefox (might not be the best way to do it, but it does the trick)
#RUN     bash -c 'echo "firefox" >> /.bashrc'

###########################
## install Audio Recorder #
###########################
FROM openjdk:11
FROM maven:3.6.3-jdk-11 AS build

COPY ./src /home/app/src
COPY ./pom.xml /home/app
RUN mvn --file /home/app/pom.xml install -Dmaven.test.skip=true
RUN mvn --file /home/app/pom.xml clean package assembly:single -Dtest=!AudioRecorderTest

ENTRYPOINT ["java", "-jar", "/home/app/target/Dream-Audio-Recorder-1.0-SNAPSHOT-jar-with-dependencies.jar"]
