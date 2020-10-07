#!/usr/bin/env bash

# Copies deployment configs to dist folder.
deployment="$1"

# Check if we got the deployment.
if [ -z $deployment ]; then
  deployment="default"
fi

# Check if the given deployment exists.
deployment_path="deployments/$deployment"
if [ ! -d $deployment_path ]; then
  echo "Given deployment not found: $deployment_path"
  exit 1
fi

echo "Copying configurations for deployment:"
cp -r -v $deployment_path/config.json temp.json

KALKI_DB_VER="1.8.0"
docker build --network=host --build-arg KALKI_DB_VER="${KALKI_DB_VER}" -t kalki/kalki-dashboard .
