#!/bin/sh
mvn clean jacoco:prepare-agent test jacoco:report
echo wait for firefox to open report...
firefox target/site/jacoco/index.html &