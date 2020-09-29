#!/bin/sh

#
# install: xterm, socat
# TODO: connect to X11 display
#

CONTAINER_NAME=audio-recorder

# remove old docker container
docker stop $CONTAINER_NAME
docker rm -f $CONTAINER_NAME

# open connection X11 display in separate console
#xterm -hold -e "socat TCP-LISTEN:6000,reuseaddr,fork UNIX-CLIENT:\"$DISPLAY\"" &
#konsole --noclose -e "socat TCP-LISTEN:6000,reuseaddr,fork UNIX-CLIENT:\"$DISPLAY\""

# run container and use local display
# --rm : auto remove container if it exists
docker run --rm \
       -v /tmp/.X11-unix:/tmp/.X11-unix \
#       -e DISPLAY=$(ipconfig getifaddr en0):0 \
       --name $CONTAINER_NAME \
       $CONTAINER_NAME