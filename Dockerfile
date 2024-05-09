# crie a imagem com:
# docker build -t agenda .
# -t especifica o "tag" da imagem (nome)
#
FROM openjdk:16-alpine3.13
WORKDIR /app
COPY . .
RUN ./mvnw dependency:go-offline
RUN ./mvnw package 
EXPOSE 8080
CMD ./mvnw spring-boot:run
# alternativamente CMD [ "./mvnw", "spring-boot:run" ]
# ou ainda ENTRYPOINT [ "./mvnw", "spring-boot:run" ]
# a diferença é que entry point não pode ser substituído por comando, ex., "docker run agenda comando"