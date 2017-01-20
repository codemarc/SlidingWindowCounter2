# AlpineLinux with a Open JDK8
FROM alpine:3.4
MAINTAINER Marc J. Greenberg <codemarc@gmail.com>

RUN apk add --no-cache openjdk8 && \
    ln -sf "${JAVA_HOME}/bin/"* "/usr/bin/"

ENV JAVA_HOME=/usr/lib/jvm/default-jvm

EXPOSE 8080

# Add the web app and jetty runner
WORKDIR /swc2
COPY jetty-runner.jar .
COPY swc2.war .
COPY swc2.sh .

CMD "./swc2.sh"
