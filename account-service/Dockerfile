# Imagem base Ubuntu
FROM ubuntu:22.04

LABEL authors="Abrupt"

RUN apt-get update && apt-get install -y openjdk-17-jdk wget ca-certificates && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]