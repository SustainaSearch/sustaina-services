# sustaina-services


## Development

### Run locally

1) `./gradlew clean` (optional)
2) `./gradlew runPlayBinary -t` (omit `-t` if you don't want to enable continous build)

Note: For some reason these two Gradle tasks needs to be executed separately in the specified order, i.e. 

`.gradlew clean runPlayBinary -t`

doesn't work.

### API documentation
[http://localhost:9000/api-doc/](http://localhost:9000/api-doc/)
