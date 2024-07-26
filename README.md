# Agregator
Сервис, возвращающий данные о курсе заданных валют с сайта [currate.ru](https://currate.ru/) и погоде с сайта [openweathermap.org](https://openweathermap.org) на ближайшие 5 дней, с шагом в 3 часа.

## Технологии
Разработка:
- Java 17
- Gradle
- Spring Boot
- REST

Тестирование:
- Mockito
- Rest Assured

## Инструкция по развертыванию проекта:
- [Скачать данный репозиторий](https://github.com/FisanovE/agregator)
- Запустить метод main класса AgregatorApplication
- Сервис доступен по адресу: http://localhost:8080

### Требования
Для запуска проекта необходим Java SE 17.

Информация возвращается при запросе по эндпоинту: `GET /aggregate`
Запрос принимает следующие параметры:  
- currency - строка со значением валютной пары, по которой необходимо получить текущий курс. Может принимать несколько значений через запятую, например: `currency=USDRUB,EURRUB`
- city - название города, по которому необходимо получить прогноз погоды. Например, `city=Magnitogorsk`;
- lat - координаты широты;
- lon - координаты долготы.  
Прмечание: При наличии в параметрах одновременно названия города и координат местности, прогноз погоды выдаётся по названию города.

Возможные варианты запросов:  
http://localhost:8080/aggregate?currency=USDRUB&currency=EURRUB&city=Magnitogorsk  
http://localhost:8080/aggregate?currency=USDRUB,EURRUB&lat=53.4242184&lon=58.983136