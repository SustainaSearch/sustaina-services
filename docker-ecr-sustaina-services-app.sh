#!/usr/bin/env bash
$(aws ecr get-login --no-include-email --region eu-north-1)
./docker-build-sustaina-services-app.sh
docker tag sustaina-services-app:latest 386503360896.dkr.ecr.eu-north-1.amazonaws.com/sustaina-services-app:latest
docker push 386503360896.dkr.ecr.eu-north-1.amazonaws.com/sustaina-services-app:latest