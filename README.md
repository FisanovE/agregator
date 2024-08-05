# Agregator
Сервис, возвращающий данные о курсе заданных валют с сайта [currate.ru](https://currate.ru/) и погоде с сайта [openweathermap.org](https://openweathermap.org) на ближайшие 5 дней, с шагом в 3 часа.

## Технологии
#### Разработка:
- Java 17
- Gradle
- Spring Boot
- REST

#### Тестирование:
- Mockito
- Rest Assured

## Инструкция по развертыванию проекта:
- [Скачать данный репозиторий](https://github.com/FisanovE/agregator)
- Собрать проект в jar-файл при помощи команды: `./gradlew build`
- Запустить терминал `cmd.exe`
- Перейти в папку с файлом `agregator-0.0.1-SNAPSHOT.jar`: 

```bash
cd C:\YourFolder\agregator\build\libs
```
- Запустить приложение при помощи команды:
```bash
java -Dfile.encoding="UTF-8" -Dsun.jnu.encoding="UTF-8" -Djava.net.preferIPv4Stack=true -Djdk.httpclient.HttpClient.log=requests -Dspring.config.location=C:\YourFolder\java\agregator\src\main\resources\application.properties -jar agregator-0.0.1-SNAPSHOT.jar
```

### Параметры запуска:

- `-Dfile.encoding="UTF-8"`: Устанавливает кодировку файла на UTF-8.
- `-Dsun.jnu.encoding="UTF-8"`: Устанавливает кодировку ввода-вывода на UTF-8.
- `-Djava.net.preferIPv4Stack=true`: Принудительное использование IPv4 вместо IPv6.
- `-Djdk.httpclient.HttpClient.log=requests`: Включает логирование HTTP-запросов, отправляемых `HttpClient`.
- `-Dspring.config.location=C:\YourFolder\agregator\src\main\resources\application.properties`: Указывает местоположение файла конфигурации Spring.
- `-jar agregator-0.0.1-SNAPSHOT.jar`: Указывает, что нужно запустить JAR-файл вашего приложения.
- `-Dcurrency.token=key`: Устанавливает ключ (токен) для работы с сервисом валюты.
- `-Dweather.token=key`: Устанавливает ключ (токен) для работы с сервисом погоды.

Информация возвращается при запросе по эндпоинту: `GET /aggregate`  
Запрос принимает следующие параметры:
- `currency` - строка со значением валютной пары (обязательный параметр), по которой необходимо получить текущий курс. Может принимать несколько значений через запятую, например: `currency=USDRUB,EURRUB`
- `city` - название города, по которому необходимо получить прогноз погоды. Например, `city=Magnitogorsk`;
- `lat` - координаты широты. Например, `lat=53.4242184`;
- `lon` - координаты долготы. Например, `lon=58.983136`.  
  **Прмечание**: При наличии в параметрах одновременно названия города и координат местности, прогноз погоды выдаётся по названию города.

Возможные варианты запросов:  
http://localhost:8080/aggregate?currency=USDRUB&currency=EURRUB&city=Magnitogorsk  
http://localhost:8080/aggregate?currency=USDRUB,EURRUB&lat=53.4242184&lon=58.983136

### Требования
Для запуска проекта необходим Java SE 17.

## Логирование

Приложение использует `Log4j2` для логирования событий.

### Конфигурация логирования

Прилагаемый файл конфигурации `log4j2.xml` настраивает логирование следующим образом:

- Логи записываются в файл `agregator.log`.
- Логи архивируются ежедневно, и каждое новое имя файла будет иметь формат `agregator.log.MM-dd-yyyy`.
- Уровень логирования по умолчанию установлен на `INFO`.

Пример конфигурации файла `log4j2.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<Configuration status="FATAL" monitorInterval="5">  
    <Properties>  
        <Property name="pattern">version %date{ISO8601} %logger{1.}.%method(): %mask{%message} %exception%n</Property>  
    </Properties>  
  
    <Appenders>  
        <RollingRandomAccessFile name="fileAppender" fileName="agregator.log"  
                                 filePattern="agregator.log.%d{MM-dd-yyyy}" ignoreExceptions="false">  
            <PatternLayout pattern="${pattern}"/>  
            <Policies>  
                <TimeBasedTriggeringPolicy/>  
            </Policies>  
        </RollingRandomAccessFile>  
    </Appenders>  
  
    <Loggers>  
        <Root level="INFO">  
            <AppenderRef ref="fileAppender"/>  
        </Root>  
    </Loggers>  
</Configuration>
```