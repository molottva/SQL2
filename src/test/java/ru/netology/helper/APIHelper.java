package ru.netology.helper;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.CardData;
import ru.netology.data.TransferData;
import ru.netology.data.UserData;
import ru.netology.data.UserVerifyData;

import static io.restassured.RestAssured.given;

public class APIHelper {
    private static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void auth(UserData userData) {
        Gson gson = new Gson();
        String user = gson.toJson(userData);
        given()
                .spec(spec)
                .body(user)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }

    public static String verification(UserVerifyData verifyData) {
        Gson gson = new Gson();
        String verify = gson.toJson(verifyData);
        return given()
                .spec(spec)
                .body(verify)
                .when()
                .post("/api/auth/verification")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    public static CardData[] getCards(String token) {
        spec.header("Authorization", "Bearer " + token);
        return given()
                .spec(spec)
                .when()
                .get("/api/cards")
                .then()
                .statusCode(200)
                .extract()
                .response().getBody().as(CardData[].class);
    }

    public static void transfer(TransferData transferData, String token) {
        Gson gson = new Gson();
        String transfer = gson.toJson(transferData);
        spec.header("Authorization", "Bearer " + token);
        given()
                .spec(spec)
                .body(transfer)
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(200);
    }
}
