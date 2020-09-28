#!/bin/sh

CONTAINER_NAME=audio-recorder

# change to directory where Dockerfile is
cd ..
# -t means tag. name of the container
docker build -t $CONTAINER_NAME .
