# Container built from solr/Dockerfile
FROM solr:8.2.0

MAINTAINER SustainaSearch

# Override the HEAP setting
ENV SOLR_HEAP_MEMORY 512m

ADD --chown=solr:solr src/main/resources/sustaina-products /var/solr/data/sustaina-products