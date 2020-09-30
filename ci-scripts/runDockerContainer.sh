#!/bin/bash

#
# install: xterm, socat
# TODO: connect to X11 display
#

CONTAINER_NAME=audio-recorder

# remove old docker container
#docker stop $CONTAINER_NAME
#docker rm -f $CONTAINER_NAME

# open connection X11 display in separate console
#xterm -hold -e "socat TCP-LISTEN:6000,reuseaddr,fork UNIX-CLIENT:\"$DISPLAY\"" &
#konsole --noclose -e "socat TCP-LISTEN:6000,reuseaddr,fork UNIX-CLIENT:\"$DISPLAY\""

# run container and use local display
# --rm : auto remove container if it exists
#-e DISPLAY=$(ip -4 addr show wlo1 | grep -oP '(?<=inet\s)\d+(\.\d+){3}'):0 \
docker run -ti --rm \
       -v /tmp/.X11-unix:/tmp/.X11-unix \
       -v /usr/share/fonts:/usr/share/fonts:ro \
       -e DISPLAY=$DISPLAY \
       --security-opt label=type:container_runtime_t \
       --name $CONTAINER_NAME \
       $CONTAINER_NAME