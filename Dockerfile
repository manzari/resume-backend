FROM gradle:jdk17-alpine as build
WORKDIR /workspace/app

COPY build.gradle /workspace/app/
COPY settings.gradle /workspace/app/
COPY src /workspace/app/src

RUN gradle build

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /workspace/app/build/libs/resume-0.0.1-SNAPSHOT.jar /app/resume.jar
ENTRYPOINT ["java","-jar","/app/resume.jar"]