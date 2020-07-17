FROM kalki/kalki-db-env AS build_env

ENV SCALA_VERSION 2.12.4
ENV SBT_VERSION 1.3.8

ENV BIN_FOLDER /usr/local
ENV SCALA_FOLDER $BIN_FOLDER/scala-$SCALA_VERSION
ENV SBT_FOLDER $BIN_FOLDER/sbt

# Installing Scala and SBT, plus SBT dependencies.
RUN wget -O - https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C $BIN_FOLDER && \
    ln -s "${SCALA_FOLDER}/bin/"* "/usr/bin/" && \
    wget -O - https://piccolo.link/sbt-$SBT_VERSION.tgz | tar xfz - -C $BIN_FOLDER && \
    ln -s "${SBT_FOLDER}/bin/"* "/usr/bin/" && \
    sbt sbtVersion

COPY dashboard /dashboard
WORKDIR /dashboard
COPY temp.conf /dashboard/conf/application.conf
RUN sbt dist

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
