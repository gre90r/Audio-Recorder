#!/bin/sh

CONTAINER_NAME=audio-recorder

# remove old docker container
docker rm -f $CONTAINER_NAME

# -it : run interactively so you can see output
# -p $PORT:$PORT : make port accessible
# -v ~/.m2/:/root/.m2 : let container use my local maven repo so it does not
#                       download every time all dependencies into the container
#docker run -it -p 5900:5900 -e HOME=/ creack/firefox-vnc x11vnc -forever -usepw -create \
#       -v ~/.m2/:/root/.m2 --name $CONTAINER_NAME $CONTAINER_NAME

# run container and use local display
docker run -ti --rm \
       -e DISPLAY=$DISPLAY \
       -v /tmp/.X11-unix:/tmp/.X11-unix \
       $CONTAINER_NAME