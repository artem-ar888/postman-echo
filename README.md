[![Java CI with Gradle](https://github.com/artem-ar888/postman-echo/actions/workflows/gradle.yml/badge.svg)](https://github.com/artem-ar888/postman-echo/actions/workflows/gradle.yml)

# Домашнее задание к занятию «1.2. Тестирование API, CI»

В качестве результата пришлите ссылку на ваш GitHub-проект в личном кабинете студента на сайте [netology.ru](https://netology.ru).

Первые две задачи этого занятия нужно делать в одном репозитории.

**Важно**: если у вас что-то не получилось, то оформляйте issue [по установленным правилам](https://github.com/netology-code/aqa-homeworks/blob/master/report-requirements.md).

**Важно**: не делайте ДЗ всех занятий в одном репозитории. Иначе вам потом придётся достаточно сложно подключать системы Continuous integration.

## Как сдавать задачи

1. Инициализируйте на своём компьютере пустой Git-репозиторий.
1. Добавьте в него готовый файл [.gitignore](https://github.com/netology-code/aqa-homeworks/blob/master/.gitignore).
1. Добавьте в этот же каталог код вашего приложения, код проекта для решения первой и второй задачи вы можете найти в [репозитории с учебным кодом](https://github.com/netology-code/aqa-code/tree/master/api-ci/rest).
1. Сделайте необходимые коммиты.
1. Создайте публичный репозиторий на GitHub и свяжите свой локальный репозиторий с удалённым.
1. Сделайте пуш — удостоверьтесь, что ваш код появился на GitHub.
1. Выполните интеграцию проекта с Github Actions ([инструкция](https://github.com/netology-code/aqa-homeworks/blob/master/github-actions-integration)) или AppVeyor ([инструкция](https://github.com/netology-code/aqa-homeworks/blob/master/appveyor-integration)) на выбор, удостоверьтесь что автотесты в CI выполняются.
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru).
1. Задачи, отмеченные, как необязательные, можно не сдавать, это не повлияет на получение зачёта.

В качестве примера можете посмотреть на этот [проект](https://github.com/netology-code-samples/aqa-ci-demo). Ваш по структуре должен выглядеть так же.

## Информация по учебным JAR

JAR файл учебного сервиса поместите в папку artifacts репозитория проекта. Для запуска учебного сервиса, находясь в папке проекта, выполните в терминале команду        
```
java -jar ./artifacts/app-mbank.jar
```

Важно: если вы работаете на Windows с турецкой локалью и при запуске учебных приложений получаете Exception вида:
```
Caused by: java.lang.NoSuchFieldException: wrıteHandlerReference (тут не английская i, а именно ı или ?)
  at java.lang.Class.getDeclaredField(Unknown Source)
  at java.util.concurrent.atomic.AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl$1.run(Unknown Source)
  at java.util.concurrent.atomic.AtomicReferenceFieldUpdater$AtomicReferenceFieldUpdaterImpl$1.run(Unknown Source)
  at java.security.AccessController.doPrivileged(Native Method)
  ... 21 more
```

тогда вам все JAR нужно будет запускать командой:
```
java -Duser.language=en -Duser.country=US -jar ./artifacts/app-mbank.jar
```

Часть `./artifacts/app-mbank.jar` может меняться в зависимости от имени файла учебного сервиса и папки с ним, но первая часть для вас во всех ДЗ при запуске на вашем ПК будет именно такой.

## Задача №3: Postman Echo

**Важно**: эту задачу нужно выполнять в отдельном репозитории.

В этой задаче мы сэмулируем ситуацию, в которой SUT уже запущен, а мы из теста просто обращаемся к нему.

Есть специальный сервис, предназначенный для тестирования HTTP-запросов. Называется он [Postman Echo](https://docs.postman-echo.com). Никогда не тестируйте автоматизированными средствами веб-сервисы, если у вас нет на это письменного разрешения или если веб-сервисы специально не предназначены для этого.

Мы можем отправлять туда запросы и получать ответы.

С GET-запросами мы немного потренировались, теперь нас будут интересовать POST-запросы, а именно отправка тела запроса:

```java
// Given - When - Then
// Предусловия
given()
  .baseUri("https://postman-echo.com")
  .body("some data") // отправляемые данные (заголовки и query можно выставлять аналогично)
// Выполняемые действия
.when()
  .post("/post")
// Проверки
.then()
  .statusCode(200)
  .body(/* --> ваша проверка здесь <-- */)
;
```

Что нужно сделать:
1. Создайте новый проект на базе Gradle.
2. Добавьте необходимые зависимости. Если вы не пишите схему, то только REST assured.
3. Напишите тест, взяв сам запрос из кода выше.
4. Изучите ответ и напишите JSONPath-выражение вместо строк `/* --> ваша проверка здесь <--*/`, которое проверит, что в нужном поле хранятся отправленные вами данные. Обратите внимание, теперь у вас не массив, а объект.

Можете воспользоваться сервисами [jsonpath-tester](https://extendsclass.com/jsonpath-tester.html) или [jsonpath](https://jsonpath.com/) для быстрой проверки выражений. Описание синтаксиса [тут](https://testerslittlehelper.wordpress.com/2019/01/20/jsonpath-in-rest-assured/) и [тут](https://github.com/rest-assured/rest-assured/wiki/Usage#json-using-jsonpath).

Удостоверьтесь, что если вы будете использовать неверное выражение, то тесты упадут, в том числе и в CI.

Обратите внимание: если вам приходит вот такой объект:
```json
{
    "data": "some value"
}
```

то обратиться к нему с помощью JSONPath можно вот так: `data`, например: `.body("data", equalTo("some value"))`. То есть обращение к полю верхнеуровневого объекта, который называется безымянным, идёт без точки. В примере на лекциях у нас был массив и мы сразу обращались `[0]` — то же самое.

Если соберётесь отправлять текст не на латинице, то вам нужно будет выставлять кодировку, например, UTF-8:
```java
given()
  .baseUri("https://postman-echo.com")
  .contentType("text/plain; charset=UTF-8")
  .body("some data")
.when()
  .post("/post")
.then()
  .statusCode(200)
  .body(/* --> ваша проверка здесь <-- */)
;
```

Пришлите на проверку ссылку на ваш репозиторий. Удостоверьтесь, что в истории сборки были как success, так и fail, иначе будет не видно, как вы проверяли, что сборка падает в CI.
