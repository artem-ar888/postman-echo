package ru.netology.rest.postmanecho;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    @Test
    public void shouldReturnBodyWithPost() {
        // Given - When - Then
        // Предусловия
        given()
                .baseUri("https://postman-echo.com")
                .contentType("text/plain; charset=UTF-8") // если текст не на латинице
                .body("данные") // отправляемые данные (заголовки и query можно выставлять аналогично)
                // Выполняемые действия
                .when()
                .post("/post")
                // Проверки
                .then()
                .statusCode(200)
                .body("data", equalTo("данные"))
        ;
    }

    @Test
    public void shouldReturnBodyWithGet() {
        // Given - When - Then
        // Предусловия
        given()
                .baseUri("https://postman-echo.com")
                // Выполняемые действия
                .when()
                .get("/get?foo1=bar1&foo2=bar2")
                // Проверки
                .then()
                .statusCode(200)
                .body("args.foo1", equalTo("bar1"))
        ;
    }
}