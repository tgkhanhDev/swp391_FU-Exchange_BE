FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/fu-exchange-3.2.5.jar /app/fu-exchange-3.2.5.jar

EXPOSE 8080

CMD ["java", "-jar", "fu-exchange-3.2.5.jar"]