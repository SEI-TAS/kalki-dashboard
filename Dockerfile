FROM kalki/kalki-db-env AS build_env

ENV SCALA_VERSION 2.12.6
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

EXPOSE 9000
WORKDIR /
COPY dashboard /dashboard
WORKDIR /dashboard

ENTRYPOINT ["sbt", "run"]

