#!/usr/bin/env bash

compose_file="docker-compose-dev.yml"
dev="$1"
if [ -z ${dev} ]; then
  compose_file="docker-compose.yml"
fi

export HOST_TZ=$(cat /etc/timezone)
docker-compose -f ${compose_file} up -d
bash compose_logs.sh
