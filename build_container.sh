#!/usr/bin/env bash

# Copies deployment configs to dist folder.
deployment="$1"

# Check if we got the deployment.
if [ -z $deployment ]; then
  deployment = "default"
fi

# Check if the given deployment exists.
deployment_path="deployments/$deployment"
if [ ! -d $deployment_path ]; then
  echo "Given deployment not found: $deployment_path"
  exit 1
fi

echo "Copying configurations for deployment:"
cp -r -v $deployment_path/application.conf temp.conf

docker-compose build
