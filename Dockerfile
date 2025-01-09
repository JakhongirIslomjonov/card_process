# Java 17 bilan ishlaydigan engil OpenJDK image
FROM openjdk:17-jdk-slim

# Muallif haqida ma'lumot
LABEL authors="Jaxongir"

# Ilova uchun ishchi katalogni belgilash
WORKDIR /app

# Maven build natijasida yaratilgan .jar faylni konteynerga nusxalash
COPY target/card-process-1.0-SNAPSHOT.jar app.jar

# Ilovaning ishlashi uchun portni ochish
EXPOSE 8080

# PostgreSQL bilan ishlash uchun kerakli portni ochish (agar kerak bo'lsa)
EXPOSE 5432

# Spring Boot ilovasini ishga tushirish uchun buyruq
CMD ["java", "-jar", "app.jar"]
