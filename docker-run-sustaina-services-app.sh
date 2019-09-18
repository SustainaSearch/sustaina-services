#!/usr/bin/env bash
# Gets the gateway of the running sustaina-services-solr container and strips the leading and trailing quotes
SOLR_HOST=$(docker inspect sustaina-services-solr | jq '.[].NetworkSettings.Networks.bridge.Gateway' | sed 's/.\(.*\)/\1/' | sed 's/\(.*\)./\1/')
SOLR_BASE_URL="http://$SOLR_HOST:8983"
echo 'SOLR_BASE_URL = ' $SOLR_BASE_URL
docker run -it -p 9000:9000  -e "JAVA_OPTS=-Dproducts.solr.url=$SOLR_BASE_URL -Dproduct-categories.solr.url=$SOLR_BASE_URL" --rm sustaina-services-app