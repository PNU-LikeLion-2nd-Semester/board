# step 1: Build Stage
FROM gradle:jdk17-alpine AS build
WORKDIR /home/app
COPY ./ ./
RUN ./gradlew bootJar

# step 2: Run Stage
FROM openjdk:17-alpine AS run
WORKDIR /home/app
COPY --from=build /home/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

## step 1: Single Stagae Build
#FROM openjdk:17-alpine
#WORKDIR /home/app
#COPY ./ ./
#RUN ./gradlew bootJar
#COPY *.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
