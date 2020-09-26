#!/bin/sh

export AUDIODEV=null

sudo apt-get install -y portaudio19-dev
sudo apt-get install -y libasound2-dev alsa-utils alsa-oss
sudo apt-get install -y lame
sudo apt-get install linux-modules-extra-$(uname -r)
