# weatherd
Returns the temperate in a city when given a zip code.

https://www.wunderground.com/weather/api/

## Instructions
1. Add a Weather Underground API key to `config.properties`
2. Build the .jar file: ```mvn clean package```
3. Build the Docker image and start a container: ```docker-compose up```
    - To start the container in detached mode: `docker-compose up -d`
4. http://localhost:8080/api/weather/{zip}
    - Zip code should be five integers
5. To stop the container: `docker-compose down`
