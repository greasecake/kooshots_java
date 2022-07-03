FROM maven:3.8-jdk-11-slim AS build
WORKDIR build
COPY src ./src
COPY pom.xml ./
RUN mvn -Dmaven.test.skip -f ./pom.xml clean package

FROM openjdk:11-jre-slim
WORKDIR app
COPY --from=build build/target/kooshots-1.0.0-SNAPSHOT.jar ./
ENTRYPOINT ["java", "-jar", "kooshots-1.0.0-SNAPSHOT.jar"]
