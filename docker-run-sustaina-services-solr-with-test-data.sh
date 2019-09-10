#!/usr/bin/env bash
docker stop solr-sustaina-products
docker run -p 8983:8983 --name solr-sustaina-products -v ~/solr-8.1.1/server/solr/cores/sustaina-products/:/var/solr/data/sustaina-products solr