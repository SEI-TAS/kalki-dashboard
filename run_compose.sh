#!/usr/bin/env bash

compose_file="$1"
if [ -z ${compose_file} ]; then
  compose_file="docker-compose.yml"
fi

export HOST_TZ=$(cat /etc/timezone)
docker-compose -f ${compose_file} up -d
bash compose_logs.sh
