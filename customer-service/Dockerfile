FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

USER root
RUN apk add --no-cache curl

RUN addgroup --system nonroot && adduser --system --ingroup nonroot nonroot
USER nonroot

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]