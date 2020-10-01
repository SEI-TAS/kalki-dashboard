#!/usr/bin/env bash
compose_file="docker-compose-dev.yml"
dev="$1"
if [ -z ${dev} ]; then
  compose_file="docker-compose.yml"
fi

docker-compose -f ${compose_file} down
