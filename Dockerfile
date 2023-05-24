FROM adoptopenjdk/openjdk8

WORKDIR /contact-management

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

# RUN ./mvnw dependency:resolve

COPY src ./src

EXPOSE 9000

CMD ["./mvnw", "spring-boot:run"]