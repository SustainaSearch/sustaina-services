#!/usr/bin/env bash
PRODUCTS_SOLR_BASE_URL=$1
docker run -it -p 9000:9000  -e "JAVA_OPTS=-Dproducts.solr.url=$PRODUCTS_SOLR_BASE_URL" --rm sustaina-services