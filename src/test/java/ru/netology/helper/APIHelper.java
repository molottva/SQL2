package ru.netology.helper;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIHelper {
    private static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void auth(DataHelper.UserData userData) {
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

    public static String verification(DataHelper.VerifyCode verifyData) {
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

    public static DataHelper.CardData[] getCards(String token) {
        spec.header("Authorization", "Bearer " + token);
        return given()
                .spec(spec)
                .when()
                .get("/api/cards")
                .then()
                .statusCode(200)
                .extract()
                .response().getBody().as(DataHelper.CardData[].class);
    }

    public static void transfer(DataHelper.TransferData transferData, String token) {
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
