FROM openjdk:17
EXPOSE 9090
ADD target/blood-bank-k8s.jar blood-bank-k8s.jar
ENTRYPOINT ["java", "-jar", "blood-bank-k8s.jar"]