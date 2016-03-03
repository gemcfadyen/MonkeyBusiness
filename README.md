# Apprenticeship-HttpServer
HTTP Server adhering to the Http Cob Spec

[![Build Status](https://travis-ci.org/gemcfadyen/Apprenticeship-HttpServer.svg?branch=master)](https://travis-ci.org/gemcfadyen/Apprenticeship-HttpServer)    [![Coverage Status](https://coveralls.io/repos/github/gemcfadyen/Apprenticeship-HttpServer/badge.svg?branch=master)](https://coveralls.io/github/gemcfadyen/Apprenticeship-HttpServer?branch=master)

## Setup this Http Server

#### Clone the project
`git clone git@github.com:gemcfadyen/Apprenticeship-HttpServer.git`

#### cd into the root folder
`cd Apprenticeship-HttpServer`

#### build using gradle
`./gradlew clean build`


## Setup the Acceptance tests which run against this server

#### Navigate to the vendor cob-spec directory             ----------------your {home.directory}/Documents

`cd vendor/cob-spec`


#### Package the cob-spec tests

`mvn package`

#### Starting Fitnesse Server

##### Start the Fitnesse server on port 9090.

`java -jar fitnesse.jar -p 9090`

##### Open your browser and go to http://localhost:9090. You should see the Cob Spec website.

#### Configuring Cob Spec

##### To run the tests you have to change three variables.

Navigate to the HttpTestSuite.
Click on Edit.
Update the paths for the User-Defined Variable.
SERVER_START_COMMAND is the command to start your server.
Example: java -jar /User/somebody/project/my_jar.jar
PUBLIC_DIR is the path to cob spec public folder.
Example: /User/somebody/cob_spec/public/
Remove the `-` from the start of the command so that the tests know these properties are defined.
Click Save.

#### Http Server

Your server jar needs to take two command line arguments.

-p which specifies the port to listen on. Default is 5000.
-d which specifies the directory to serve files from. Default is the PUBLIC_DIR variable.
Running Tests

To run all tests, click the Suite button.
To run the simple http request tests, first click the ResponseTestSuite link, then click the Suite button.
To run the tests that require threading, first click the SimultaniousTestSuite link, then click the Suite button.
To run a test individually, first click on the test link, then click the Test button.

