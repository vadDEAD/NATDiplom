## задание находиться в файле "TASK.md" 

# Дипломная работа по профессии «Тестировщик ПО»

## Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

#### Приложение представляет собой веб-сервис, который предоставляет возможность купить тур по определённой цене с помощью двух способов:

- Обычная оплата по дебетовой карте
- Уникальная технология: выдача кредита по данным банковской карты

### Документация:
1. [План автоматизации](https://github.com/vadDEAD/NATDiplom/blob/master/documents/Plan.md)
2. [Отчет о проведенном тестировании](https://github.com/vadDEAD/NATDiplom/blob/master/documents/Report.md)
3. [Отчет по итогам](https://github.com/vadDEAD/NATDiplom/blob/master/documents/Summary.md)


### Необходимое окружение:

* установленный Docker;
* установленная IntelliJ IDEA;
* Java 11
* браузер (Предпочтительно "Google Chrome")

### Инструкции по установке

1. Скачайте (Клонируйте) архив;
2. Запустить Docker Desktop 
3. Открыть код программы в IDEA. 
4. Запустить контейнеры (MySql, PostgreSQL) в терминале с помощью команды:
- `docker-compose up --build`
5. Запустить тестируемый сервис в терминале:
- База данных на MySQL с помощью команды:
`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
- База данных на Postgres с помощью команды:
`java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
6. Запустить тесты в терминале:
- Для работы с MySQL с помощью команды:
`./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
- Для работы с Postgres с помощью команды: 
`./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`


### Формирование отчетов Allure
Для получения отчета использовать команду:
`./gradlew allureServe`

   

