FROM openjdk:11-jre-slim
ENV DIR_HOME /opt/test
WORKDIR $DIR_HOME
COPY target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar"]