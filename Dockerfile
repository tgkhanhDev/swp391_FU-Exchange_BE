FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/fu-exchange-3.2.5.jar /app/fu-exchange-3.2.5.jar
EXPOSE 8080
CMD ["java", "-jar", "fu-exchange-3.2.5.jar"]