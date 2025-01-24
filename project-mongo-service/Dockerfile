FROM openjdk:21
EXPOSE 8082
ADD /target/project-mongo-service.jar project-mongo-service.jar
ENTRYPOINT ["java", "-jar", "project-mongo-service.jar"]