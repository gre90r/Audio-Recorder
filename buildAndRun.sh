#!/bin/sh

EXECUTABLE="target/Dream-Audio-Recorder-1.0-SNAPSHOT-jar-with-dependencies.jar"

mvn clean package assembly:single -Dmaven.test.skip=true
java -jar $EXECUTABLE &
