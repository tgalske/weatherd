# hello-from-swarm
A hello-world instance of Wildfly-Swarm using Docker.

## Instructions
1. Build the .jar file: `mvn clean package`
2. Build the Docker image and start a container: `docker-compose up`
     - To start the container in detached mode: `docker-compose up -d`
3. http://localhost:8080/hello
4. To stop the container: `docker-compose down`
