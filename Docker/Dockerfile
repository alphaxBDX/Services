FROM openjdk:17-slim

RUN apt-get update && \
    apt-get install -y postgresql-client && \
    rm -rf /var/lib/apt/lists/*

COPY ../target/Service-1.0-SNAPSHOT.jar m2chausson.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./m2chausson.jar"]