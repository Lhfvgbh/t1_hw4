# Проект №4 в рамках открытой школы Т1

## Описание
Реализация базового веб-приложения с использованием Spring Security и JWT для аутентификации и авторизации пользователей.

Для проверки функционала может быть использована коллекция Postman, лежащая в корне проекта
Коллекция содержит 2 раздела:

auth - доступен без токена JWT. Примеры запросов содержат креденшелы пользователей с ролью ADMIN и с ролью USER

users - доступен по токену JWT пользователя с ролью ADMIN

## Сборка приложения
```shell script
# запустить PostgreSQL в docker-контейнере
docker-compose up -d postgres

# загружает gradle wrapper
./gradlew wrapper

# сборка проекта, прогон unit-тестов, запуск приложения
./gradlew clean build bootRun
```