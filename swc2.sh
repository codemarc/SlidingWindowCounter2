#!/usr/bin/env sh
cd /swc2
java -Drestx.mode=prod -jar jetty-runner.jar swc2-1.0.war
