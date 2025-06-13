package com.dedalus.interview.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;


import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeResourceIT {

    private static Long employeeId;


    @Test
    @Order(1)
    public void testCreateEmployee() {
        employeeId = Long.valueOf(given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "fullName": "Test User",
                  "email": "test@example.com",
                  "phoneNumber": "0600000000",
                  "address": "123 Rue Quarkus",
                  "departmentId": %d
                }
                """.formatted(1L))
                .when()
                .post("/employees")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("fullName", equalTo("Test User"))
                .extract().path("id").toString());
    }

    @Test
    @Order(2)
    public void testGetEmployee() {
        given()
                .when()
                .get("/employees/" + employeeId)
                .then()
                .statusCode(200)
                .body("id", equalTo(employeeId.intValue()))
                .body("email", equalTo("test@example.com"));
    }

    @Test
    @Order(3)
    public void testListEmployees() {
        given()
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(4)
    public void testDeleteEmployee() {
        given()
                .when()
                .delete("/employees/" + employeeId)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    public void testEmployeeIsDeleted() {
        given()
                .when()
                .get("/employees/" + employeeId)
                .then()
                .statusCode(404);
    }
}
