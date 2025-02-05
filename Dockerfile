FROM ubuntu:latest

RUN apt update && apt-get update && apt-get install -y maven openjdk-8-jdk

COPY . /usr/src/myapp

WORKDIR /usr/src/myapp

RUN mvn install

EXPOSE 8080

CMD ["java", "-jar", "target/backend.jar"]