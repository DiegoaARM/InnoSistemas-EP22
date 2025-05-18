# Usa una imagen base con Java 21
FROM eclipse-temurin:21-jdk AS builder

# Directorio de trabajo
WORKDIR /app

# Copia el JAR construido
COPY . .

RUN ./mvnw clean package -DskipTests

#Stage 2: Run the application
FROM eclipse-temurin:21-jre

#Set the working directory
WORKDIR /app

#Expose the JAR file form the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]

#ejecutar en lineas de codigo

#para crear imagen
#docker build -t innosistemas .

#para crear container
#docker run -d -p 8080:8080 --env-file .env innosistemas