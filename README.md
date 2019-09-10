# sustaina-services


## Development

### Prerequisites

* Install JDK 8
* [Install Docker]()
* [Install the AWS Command line interface](https://docs.aws.amazon.com/cli/latest/userguide/installing.html)
* Run `aws configure` in a terminal and provide your `AWS Access Key ID`and `AWS Secret Access Key`

### 

### Run locally

#### Using provided bash script

Execute the following bash script in the root directory:

`./run-locally.sh`

#### Using Gradle tasks

Execute the following Gradle tasks in the root directory:

1) `./gradlew clean` (optional)
2) `./gradlew runPlayBinary -t` (omit `-t` if you don't want to enable continous build)

Note: For some reason these two Gradle tasks needs to be executed separately in the specified order, i.e. 

`.gradlew clean runPlayBinary -t`

doesn't work.

### API documentation
[http://localhost:9000/api-doc/](http://localhost:9000/api-doc/)
