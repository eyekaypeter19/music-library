# Song Library
A really simple spring boot maven project that provides Rest Compliant Endpoints for managing songs
## Getting started
These instructions will get you a copy of the project and run it on your local machine for development and deployment. See deployment for notes on how to deploy using docker

### Prerequisites (Running the app)
At the very minimum, you need java 11 installed and Configured correctly in PATH and JAVA_HOME environment variables. You may download java 111 from the following [link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### Running the App
1. Build the music-library top project using the following command.
 ```shell script
     mvn clean install.
 ```

2. Navigate to the directory where service-application-${VERSION}.jar is located.
3. Run the following command
```shell script
java -jar service-application-VERSION.jar
``` 
### Testing the REST endpoints locally
1. Navigate to [http"//localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html#)
2. Expand the song-controller accordion
3. Modify and fire requests as you deem appropriate.

### Testing with docker.