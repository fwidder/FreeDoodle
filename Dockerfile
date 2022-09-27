FROM eclipse-temurin:19-jre-alpine

MAINTAINER  Florian Widder <florian.widder@live.de>

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/FreeDoodle*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]