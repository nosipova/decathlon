# AS <NAME> to name this stage as maven
FROM maven:3.8.1 AS maven
LABEL MAINTAINER="nosipova@paradigmadigital.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package 

# For Java 16, 
FROM adoptopenjdk/openjdk16:alpine-jre

ARG JAR_FILE=stock-api-controller.jar

WORKDIR /opt/app

# Copy the multiple-core-implem-sample.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/controller/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","stock-api-controller.jar"]
