FROM openjdk
ARG JAR_FILE=target/job4j_cars-1.0.jar
WORKDIR job4j_cars
COPY ${JAR_FILE} app.jar
CMD java -jar app.jar