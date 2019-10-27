#!/usr/bin/env bash
# Gets the gateway of the running sustaina-services-solr container and strips the leading and trailing quotes
SOLR_HOST=$(docker inspect sustaina-services-solr | jq '.[].NetworkSettings.Networks.bridge.Gateway' | sed 's/.\(.*\)/\1/' | sed 's/\(.*\)./\1/')
SOLR_BASE_URL="http://$SOLR_HOST:8983"
echo 'SOLR_BASE_URL = ' $SOLR_BASE_URL

# TODO: get access key and secret key from key chain
AWS_ACCESS_KEY="AKIAVT7LFIGAJRT4LFPA"
AWS_SECRET_KEY=""
AWS_REGION="us-east-1"
docker run -it -p 9000:9000  -e "JAVA_OPTS=-Dproducts.solr.url=$SOLR_BASE_URL -Daws.access.key=$AWS_ACCESS_KEY -Daws.secret.key=$AWS_SECRET_KEY -Daws.region=$AWS_REGION" --rm sustaina-services-app