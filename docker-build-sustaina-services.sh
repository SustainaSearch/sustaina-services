#!/usr/bin/env bash
./gradlew clean dist

set -x
unzip -d sustaina-services-app/build/svc sustaina-services-app/build/distributions/*.zip
mv sustaina-services-app/build/svc/*/* sustaina-services-app/build/svc/
rm sustaina-services-app/build/svc/bin/*.bat
mv sustaina-services-app/build/svc/bin/* sustaina-services-app/build/svc/bin/start

docker build -t sustaina-services sustaina-services-app