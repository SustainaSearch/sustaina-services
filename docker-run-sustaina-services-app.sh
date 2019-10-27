#!/usr/bin/env bash
# Gets the gateway of the running sustaina-services-solr container and strips the leading and trailing quotes
SOLR_HOST=$(docker inspect sustaina-services-solr | jq '.[].NetworkSettings.Networks.bridge.Gateway' | sed 's/.\(.*\)/\1/' | sed 's/\(.*\)./\1/')
SOLR_BASE_URL="http://$SOLR_HOST:8983"
echo 'SOLR_BASE_URL = '$SOLR_BASE_URL

# Gets the sustaina_bot AWS access key and secret key from the Keychain
AWS_ACCESS_KEY=$(security find-generic-password -a sustaina_bot -ws aws.access.key)
echo 'AWS_ACCESS_KEY = '"${AWS_ACCESS_KEY:0:4}****************"
AWS_SECRET_KEY=$(security find-generic-password -a sustaina_bot -ws aws.secret.key)
echo 'AWS_SECRET_KEY = '"${AWS_SECRET_KEY:0:4}************************************"

AWS_REGION="us-east-1"
echo 'AWS_REGION = '"$AWS_REGION"

docker run -it -p 9000:9000  -e "JAVA_OPTS=-Dproducts.solr.url=$SOLR_BASE_URL -Daws.access.key=$AWS_ACCESS_KEY -Daws.secret.key=$AWS_SECRET_KEY -Daws.region=$AWS_REGION" --rm sustaina-services-app