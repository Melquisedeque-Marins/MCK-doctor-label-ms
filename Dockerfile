FROM openjdk:17

WORKDIR /app

ENV PROFILE=prod

COPY /target/doctor-labelling-ms-0.0.1-SNAPSHOT.jar /app/doctor-labelling-ms.jar

ENTRYPOINT [ "java", "-jar", "doctor-labelling-ms.jar" ]

EXPOSE 8080
