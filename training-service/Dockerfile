FROM openjdk:21
EXPOSE 8082
ADD /target/training-service.jar training-service.jar
ENTRYPOINT ["java", "-jar", "training-service.jar"]