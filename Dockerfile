FROM eclipse-temurin:17.0.10_7-jre-jammy
LABEL authors="kmahadevan"
RUN mkdir /app
COPY target/master.jar /app/master.jar
WORKDIR /app
EXPOSE 10010
ENTRYPOINT ["java", "-jar", "master.jar"]