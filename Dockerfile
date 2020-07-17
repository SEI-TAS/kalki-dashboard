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

WORKDIR /
COPY dashboard /dashboard
WORKDIR /dashboard
COPY temp.conf /dashboard/conf/application.conf
RUN sbt assembly

ENTRYPOINT ["bash"]

# Second stage: actual run environment.
FROM openjdk:8-jre-alpine

ENV SCALA_VERSION 2.12.4
EXPOSE 9000

ARG PROJECT_NAME=kalki-dashboard
ARG PROJECT_VERSION=1.6.0

RUN mkdir -p /dashboard
WORKDIR /dashboard

COPY --from=build_env /dashboard/target/scala_${SCALA_VERSION}/${PROJECT_NAME}-assembly-${PROJECT_VERSION}.jar /dashboard/kalki-dashboard.jar

WORKDIR /$PROJECT_NAME
ENTRYPOINT ["java", "-jar", "kalki-dashboard.jar"]
