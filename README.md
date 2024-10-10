# Habit Tracker

## Описание
Habit Tracker - это консольное приложение, написанное на чистом Java, предназначенное для отслеживания привычек пользователей. Пользователи могут регистрироваться, логиниться, добавлять и удалять привычки, а также просматривать свои текущие привычки.

## Оглавление
- [Функциональность](#функциональность)
- [Технологии](#технологии)
- [Установка](#установка)
- [Использование](#использование)
- [Тестирование](#тестирование)

## Функциональность
- Регистрация и авторизация пользователей
- Управление привычками (добавление, удаление, просмотр)
- Настройка частоты выполнения привычек (ежедневно, еженедельно и т. д.)

## Технологии
- Java SE (Java 11+)
- JUnit 5 для тестирования
- Mockito для создания заглушек (mocks)
- Maven для управления зависимостями

## Установка
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/VageGG/Y_LAB-Habit-tracker-app.git

2. Перейдите в директорию проекта:
   ```bash
   cd Y_LAB-Habit-tracker-app

3. Соберите проект с помощью Maven:
   ```bash
   mvn clean install

## Использование
   ```bash
   mvn exec:java -Dexec.mainClass="HabitTrackerApp"

## Тестирование
   ```bash
   mvn test