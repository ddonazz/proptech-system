FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY user-service/pom.xml ./user-service/

RUN mvn -f user-service/pom.xml dependency:go-offline

COPY user-service/src ./user-service/src

RUN mvn -f user-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

ARG JAR_FILE=user-service/target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]