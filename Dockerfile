FROM openjdk:latest
COPY /target/weatherd-swarm.jar swarm.jar
COPY config.properties config.properties
EXPOSE 8080
CMD ["java", "-jar", "swarm.jar"]