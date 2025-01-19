FROM openjdk:21
EXPOSE 8081
ADD /target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]