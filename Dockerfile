FROM maven:3.8.6-openjdk-18 as build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
ENV PROFILE=prod
COPY --from=build ./build/target/*.jar ./doctor-labelling-ms.jar
ENTRYPOINT [ "java", "-jar", "doctor-labelling-ms.jar" ]
EXPOSE 8080
