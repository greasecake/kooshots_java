FROM openjdk:11-jre-slim
WORKDIR app
COPY /target/kooshots-1.0.0-SNAPSHOT.jar ./
ENTRYPOINT ["java", "-jar", "kooshots-1.0.0-SNAPSHOT.jar"]
