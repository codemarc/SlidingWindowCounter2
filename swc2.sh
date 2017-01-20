#!/usr/bin/env sh
cd /swc2
java -Drestx.mode=prod -Drestx.http.XForwardedSupport=all -jar jetty-runner.jar swc2.war
