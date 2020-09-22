# First stage: getting kalki-db
ARG KALKI_DB_VER="latest"
FROM kalki/kalki-db-env:$KALKI_DB_VER AS kalki_db

################################################################################################
# Second stage: build env.
FROM openjdk:8-jdk-alpine AS build_env

RUN apk --no-cache add bash wget

# Installing SBT
ENV SBT_VERSION 1.3.8
RUN wget -O sbt.tgz https://piccolo.link/sbt-$SBT_VERSION.tgz
RUN tar -zxvf sbt.tgz
RUN /sbt/bin/sbt sbtVersion

# Get deployed kalki-db from prev stage.
COPY --from=kalki_db /home/gradle/.m2 /root/.m2

# Copying code and conf
COPY dashboard /dashboard
COPY temp.json /dashboard/config.json

# Getting deps, compiling and creating dist.
WORKDIR /dashboard
RUN /sbt/bin/sbt update
RUN /sbt/bin/sbt dist

################################################################################################
# Third stage: actual run environment.
FROM openjdk:8-jre-alpine

RUN apk --no-cache add bash

EXPOSE 9000

ARG PROJECT_NAME=kalki-dashboard
ARG PROJECT_VERSION=1.7.0

COPY --from=build_env /dashboard/target/universal/$PROJECT_NAME-$PROJECT_VERSION.zip /$PROJECT_NAME.zip
RUN unzip $PROJECT_NAME.zip && \
    rm $PROJECT_NAME.zip

WORKDIR /$PROJECT_NAME-$PROJECT_VERSION
ENTRYPOINT ["bin/kalki-dashboard"]
