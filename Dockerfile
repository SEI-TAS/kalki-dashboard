# From https://semaphoreci.com/community/tutorials/dockerizing-a-java-play-application
# See above for explaination of each line.
FROM openjdk:8

ENV PROJECT_HOME /usr/src
RUN mkdir -p $PROJECT_HOME/activator $PROJECT_HOME/app

WORKDIR $PROJECT_HOME/activator

RUN wget http://downloads.typesafe.com/typesafe-activator/1.3.10/typesafe-activator-1.3.10.zip && \
    unzip typesafe-activator-1.3.10.zip

ENV PATH $PROJECT_HOME/activator/activator-dist-1.3.10/bin:$PATH
ENV PATH $PROJECT_HOME/build/target/universal/stage/bin:$PATH
COPY . $PROJECT_HOME/app
WORKDIR $PROJECT_HOME/app/dashboard
EXPOSE 9000

# Hacky way to download the activator dependencies.
# The server gets terminated automatically because STDIN gets closed at EOF.
# https://github.com/playframework/playframework/issues/4001
RUN activator run

CMD ["activator", "run"]
