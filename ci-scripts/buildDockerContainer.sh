#!/bin/sh

CONTAINER_NAME=audio-recorder

#####################################
# build container (from Dockerfile) #
#####################################
# change to directoy where Dockerfile is
cd ..
# -t means tag. name of the container
docker build -t $CONTAINER_NAME --name $CONTAINER_NAME .
# remove old docker container
docker rm -f $CONTAINER_NAME

###################
# run application #
###################
# -it : run interactively so you can see output
# -p $PORT:$PORT : make port accessible
# -v ~/.m2/:/root/.m2 : let container use my local maven repo so it does not
#                       download every time all dependencies into the container
#docker run -it -p $PORT:$PORT -v ~/.m2/:/root/.m2 --name $CONTAINER_NAME $CONTAINER_NAME
