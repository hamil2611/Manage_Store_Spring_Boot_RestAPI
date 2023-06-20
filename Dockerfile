FROM openjdk:17
LABEL maintainer="Manage_Store"
ADD target/ManageStore-0.0.1-SNAPSHOT.jar ManageStore-0.0.1-SNAPSHOT.jar.original
ENTRYPOINT ["java","-jar","ManageStore-0.0.1-SNAPSHOT.jar.original" ]
