package com.modulenotfound.motivation.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KidControllerIT {

    @Value("${local.server.port}")
    public int serverPort;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;
    }

    @Test
    public void addKid() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        given()
                .contentType(ContentType.JSON)
                .body(jsonFromFile)

                .when()
                .post("/api/kids")

                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("kid1"))
                .body("point", is(10))
                .body("id", not(0));

    }

    @Test
    public void getAllKids() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        given()
                .contentType(ContentType.JSON)
                .body(jsonFromFile)

                .when()
                .post("/api/kids");

        given()
                .get("/api/kids")
                .then()
                .log().all()
                .statusCode(200)
                .body("[0].name", is("kid1"))
                .body("[0].point", is(10));
    }

    @Test
    public void getKid() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        int id = given()
                .contentType(ContentType.JSON)
                .body(jsonFromFile)

                .when()
                .post("/api/kids")
                .then()
                .extract()
                .path("id");

        given()
                .pathParam("id", id)
                .when()
                .get("/api/kid/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("kid1"))
                .body("point", is(10))
                .body("id", is(id));
    }

    @Test
    public void getKid_idNotExist() {
        given()
                .pathParam("id", 2345)
                .when()
                .get("/api/kid/{id}")
                .then()
                .log().all()
                .statusCode(400)
                .body("code", is(100));
    }

    @Test
    public void deleteKid() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        int id = given()
                .contentType(ContentType.JSON)
                .body(jsonFromFile)

                .when()
                .post("/api/kids")
                .then()
                .extract()
                .path("id");

        given()
                .pathParam("id", id)
                .when()
                .delete("/api/kid/{id}")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void deleteKid_idNotExist() {
        given()
                .pathParam("id", 2345)
                .when()
                .delete("/api/kid/{id}")
                .then()
                .log().all()
                .statusCode(400)
                .body("code", is(100));
    }
}