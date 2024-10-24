package steps.userprofile;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiRequestQuery {

    @Step("Perform Get method")
    public static Response doGet(){
        return given()
                .when()
                .get()
                .then().extract().response();
    }

    @Step("Perform Post method with [{obj}]")
    public static Response doPost(Object obj){
        return given()
                .body(obj)
                .when()
                .post()
                .then().extract().response();
    }

    @Step("Perform Post method")
    public static Response doPost(){
        return given()
                .when()
                .post()
                .then().extract().response();
    }

    @Step("Perform Patch method with [{obj}]")
    public static Response doPatch(Object obj){
        return given()
                .body(obj)
                .when()
                .patch()
                .then().extract().response();
    }

    @Step("Perform Patch to rollback to previous entity properties")
    public static Response doRollBackPatch(Object obj){
        return doPatch(obj);
    }

    @Step("Perform Patch method")
    public static Response doPatch(){
        return given()
                .when()
                .patch()
                .then().extract().response();
    }
}
