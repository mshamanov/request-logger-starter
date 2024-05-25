# Spring Boot Starter для логирования HTTP запросов

## Описание:

Spring Boot Starter, предоставляющий возможность логировать HTTP запросы в
приложении на базе Spring Boot.

## Требования:

Spring Boot Starter должен предоставлять возможность логировать все входящие и
исходящие HTTP запросы и ответы вашего приложения.
Логирование должно включать в себя метод запроса, URL, заголовки запроса и
ответа, код ответа, время обработки запроса и т.д.

## Технологии/зависимости

В рамках проекта используется:<br>
[![Java21](https://img.shields.io/badge/JAVA-21-3a75b0?logo=openjdk&logoColor=white)](https://adoptium.net/download/)
[![Spring Boot v3](https://img.shields.io/badge/SpringBoot-6DB33F?logo=Spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?logo=Apache-Maven&logoColor=white)](https://maven.apache.org)

### JDK

Проект написан с использованием JDK (Java) версии 21, который можно скачать,
например, по этой ссылке: https://adoptium.net/download

### Среда разработки

Можно
использовать [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
или [VS Code](https://code.visualstudio.com),
либо любую другую совместимую с вышеуказанными технологиями среду разработки.

## Основная структура проекта

Приложение разделено на два модуля: `request-logger-starter`
и `request-logger-example`. Каждый модуль имеет свой набор классов и
интерфейсов. Модуль `request-logger-starter` представляет собой Spring Boot
Starter для логирования входящих запросов и ответов от сервера, подключается к
приложению на базе Spring Boot и автоматически конфигурируется при старте
приложения, в зависимости от пользовательских настроек.
Модуль `request-logger-example` выступает в качестве примера работы
вышеуказанного стартера, используется для демонстрации его возможностей. Таким
образом, модуль-стартер `request-logger-starter` уже подключен к
модулю `request-logger-example` в качестве зависимости и Вам нет необходимости
подключать его самостоятельно.

### Структура request-logger-starter (стартер)

* `LoggingRequestStarterApplication` - точка входа в приложение,
* `config/*` - классы для настройки окружения,
* `filter/*` - классы и интерфейсы для логирования сетевых запросов

#### Конфигурация

- `src/main/resources/META-INF/spring/*` - мета-данные для загрузки стартера
- `src/main/resources/application.yaml` - основной файл конфигурации
  приложения

### Структура request-logger-example (демо-приложение)

* `RequestLoggerExampleApplication` - точка входа в приложение,
* `advice/GlobalControllerAdvice` - класс для обработки ошибок контроллера,
* `controller/ExampleController` - класс контроллера,
* `model/User` - класс для передачи данных, представляющий пользователя

#### Конфигурация

- `src/main/resources/application.yaml` - основной файл конфигурации
  приложения

При необходимости измените следующие параметры в файле конфигурации:

```
# Параметр для включения/выключения логирования сетевых запросов
request-logger:
  enabled: true
  
# Параметр для смены порта, на котором будет работать приложение 
server:
  port: 8080
```

Вы также можете создать свой собственный компонент для логирования вместо
предоставленного по-умолчанию. Для этого Вам необходимо реализовать интерфейс
`LoggingRequestHandler` из модуля `request-logger-starter` или наследовать
класс `AbstractLoggingRequestHandler`, обладающий набором нескольких
базовых методов для получения информации из запросов и ответов, переопределив
один единственный метод `log` с тремя параметрами. Не забудьте отметить свою
собственную реализацию компонента аннотацией `@Bean` или `@Component` в
зависимости от способа его инициализации.

## Запуск приложения

Скачайте
приложение [архивом](https://github.com/mshamanov/request-logger-starter/archive/refs/heads/master.zip)
или с помощью следующей команды:

```
git clone https://github.com/mshamanov/request-logger-starter.git
```

Далее, если Maven уже установлен на Вашем компьютере, то Вы можете запустить
следующую команду **из корня проекта**:

```
mvn -pl request-logger-example -am spring-boot:run
```

Если Maven не установлен на Вашем компьютере, то Вы можете запустить следующую
команду:

```
./mvnw -pl request-logger-example -am spring-boot:run
```

В том случае, если запуск будет осуществляться непосредственно из среды
разработки, то Вы можете открыть проект и запустить метод `main` в
классе `RequestLoggerExampleApplication` из
модуля [request-logger-example](request-logger-example).

При успешном запуске модуля [request-logger-example](request-logger-example)
будет запущен REST-сервис во время работы которого Вам будет доступна
пользовательская среда Swagger-UI для взаимодействия с REST-сервисом по ссылке:
http://localhost:8080/swagger-ui/index.html

Вы также можете воспользоваться любым другим инструментом для обращения через
REST API, как, например, [Postman](https://www.postman.com).
Данные для обращения к REST-сервису Вы также найдёте через пользовательскую
среду Swagger-UI по ссылке выше.

Совершите несколько запросов в адрес REST-сервиса, чтобы убедиться в том, что
происходит их логирование в консоль вместе с ответами от сервера.

---

Таким образом, с помощью реализации своего Spring Boot Starter был создан
механизм перехвата и логирования сетевых запросов на основе фильтров Spring,
а также обеспечена настройка формата логирования сообщений, благодаря
возможности подключить собственную реализацию компонента для логирования,
заменив реализацию по-умолчанию.

## Лицензия

[![License: MIT](https://img.shields.io/badge/License-MIT-red.svg)](https://opensource.org/licenses/MIT)