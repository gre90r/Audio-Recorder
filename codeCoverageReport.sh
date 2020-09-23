#!/bin/sh
mvn test
firefox target/jacoco-report/index.html &