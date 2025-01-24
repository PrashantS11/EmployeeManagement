FROM openjdk:21
EXPOSE 8082
ADD /target/project-service.jar project-service.jar
ENTRYPOINT ["java", "-jar", "project-service.jar"]