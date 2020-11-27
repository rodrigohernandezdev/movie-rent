# Java 14 for this project
FROM adoptopenjdk/openjdk14:alpine-jre

# Add jar generated from mvn clean install
ARG JAR_FILE=target/movie-rent-1.0.0-RELEASE.jar

# cd /opt/app
WORKDIR /opt/app

# copy jar to folder opt/app
COPY ${JAR_FILE} /movie-rent-1.0.0-RELEASE.jar

# java -jar /opt/apps/movie-rent-1.0.0-RELEASE.jar
ENTRYPOINT ["java","-jar","/movie-rent-1.0.0-RELEASE.jar"]

# expose the port used for the app
EXPOSE 8080/tcp
