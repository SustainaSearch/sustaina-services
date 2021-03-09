#!/usr/bin/env bash
./docker-build-sustaina-services-app.sh

docker tag sustaina-services-app:latest parsarback/sustaina-services-app:latest
docker push parsarback/sustaina-services-app:latest