FROM kalki/kalki-db-env AS build_env

ENV SBT_VERSION 1.3.8

ENV BIN_FOLDER /home/gradle
ENV SBT_FOLDER $BIN_FOLDER/sbt

# Installing SBT
WORKDIR /home/gradle
RUN wget -O sbt.tgz https://piccolo.link/sbt-$SBT_VERSION.tgz
RUN tar -zxvf sbt.tgz

# Copying code and conf
COPY dashboard /dashboard
COPY temp.conf /dashboard/conf/application.conf

# Getting deps, compiling and creating dist.
WORKDIR /dashboard
RUN ${SBT_FOLDER}/bin/sbt dist

# Second stage: actual run environment.
FROM openjdk:8-jre-alpine

RUN apk --no-cache add bash

ARG SCALA_VERSION=2.12
EXPOSE 9000

ARG PROJECT_NAME=kalki-dashboard
ARG PROJECT_VERSION=1.6.0

COPY --from=build_env /dashboard/target/universal/$PROJECT_NAME-$PROJECT_VERSION.zip /$PROJECT_NAME.zip
RUN unzip $PROJECT_NAME.zip && \
    rm $PROJECT_NAME.zip

WORKDIR /$PROJECT_NAME-$PROJECT_VERSION
ENTRYPOINT ["bin/kalki-dashboard"]
