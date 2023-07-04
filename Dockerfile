From openjdk:19
EXPOSE 8080
ADD target/OrderManagmentAPI-0.0.1-SNAPSHOT.jar order-management-api-project.jar
ENTRYPOINT ["java","-jar","order-management-api-project.jar"]