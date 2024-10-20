FROM openjdk:17-oracle
COPY target/*.jar m2chausson.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "m2chausson.jar"]