#!/usr/bin/env bash
$(aws ecr get-login --no-include-email --region eu-north-1)
./docker-build-sustaina-services-solr.sh
docker tag sustaina-services-solr:latest 386503360896.dkr.ecr.eu-north-1.amazonaws.com/sustaina-services-solr:latest
docker push 386503360896.dkr.ecr.eu-north-1.amazonaws.com/sustaina-services-solr:latest