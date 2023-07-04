FROM openjdk:17
LABEL maintainer="Manage_Store"
WORKDIR /app
ADD target/ManageStore-0.0.1-SNAPSHOT.jar /app/ManageStore-0.0.1-SNAPSHOT.jar.original
ENTRYPOINT ["java","-jar","ManageStore-0.0.1-SNAPSHOT.jar.original" ]
