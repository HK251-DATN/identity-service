# Multi-stage build: build with Maven (JDK 25), runtime with JRE 25
FROM maven:4.0.0-rc-5-eclipse-temurin-25-alpine as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:25
WORKDIR /app
# copy built jar (support wildcard for artifact name)
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.jar"]