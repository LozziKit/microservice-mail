FROM java:openjdk-8-jre

COPY target/microservice-mail-server-1.0-SNAPSHOT.jar server.jar

#ENTRYPOINT java -jar server.jar

EXPOSE 5005

## Debug config
ENTRYPOINT java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar server.jar --io.lozzikit.smtp.address=cucumber