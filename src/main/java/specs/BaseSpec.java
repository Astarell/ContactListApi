package specs;

import global_data.GlobalData;
import global_data.GlobalEnums;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.userprofile.SiteTokenUserResponse;
import specs.settings.SpecRequestSettingsEnum;
import specs.settings.SpecResponseSettingsEnum;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BaseSpec {

    @Step("Install request spec")
    public static void installSpec(RequestSpecification requestSpec){
        RestAssured.requestSpecification = requestSpec;
    }

    @Step("Install response spec")
    public static void installSpec(ResponseSpecification responseSpec){
        RestAssured.responseSpecification = responseSpec;
    }

    @Step("Install request and response spec")
    public static void installSpec(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    @Step("Remove specs")
    public static void removeSpec(){
        RestAssured.requestSpecification = null;
        RestAssured.responseSpecification = null;
    }

    @Step("Build request spec [Settings: {requestSettings}]")
    public static RequestSpecification customRequestSpec(Map<SpecRequestSettingsEnum, Object> requestSettings){

        RequestSpecification requestSpec = new RequestSpecBuilder().build();

        if(requestSettings.containsKey(SpecRequestSettingsEnum.CONTENT_TYPE))
            requestSpec.contentType((String)requestSettings.get(SpecRequestSettingsEnum.CONTENT_TYPE));

        if(requestSettings.containsKey(SpecRequestSettingsEnum.ACCEPT))
            requestSpec.accept((String)requestSettings.get(SpecRequestSettingsEnum.ACCEPT));

        if (requestSettings.containsKey(SpecRequestSettingsEnum.TOKEN) && (Boolean)requestSettings.get(SpecRequestSettingsEnum.TOKEN))
            requestSpec.header("Authorization", ((SiteTokenUserResponse) GlobalData.getItem(GlobalEnums.ADD_USER_RESPONSE)).getToken());

        if(requestSettings.containsKey(SpecRequestSettingsEnum.BASE_URI))
            requestSpec.baseUri((String)requestSettings.get(SpecRequestSettingsEnum.BASE_URI));

        if(requestSettings.containsKey(SpecRequestSettingsEnum.PATH_URL))
            requestSpec.basePath((String)requestSettings.get(SpecRequestSettingsEnum.PATH_URL));

        return requestSpec;
    }

    @Step("Build response spec [Settings: {responseSettings}]")
    public static ResponseSpecification customResponseSpec(Map<SpecResponseSettingsEnum, Object> responseSettings){

        ResponseSpecification responseSpec = new ResponseSpecBuilder().build();

        if(responseSettings.containsKey(SpecResponseSettingsEnum.STATUS_CODE))
            responseSpec.statusCode((Integer) responseSettings.get(SpecResponseSettingsEnum.STATUS_CODE));
        if(responseSettings.containsKey(SpecResponseSettingsEnum.EXPECTED_CONTENT_TYPE))
            responseSpec.contentType((String)responseSettings.get(SpecResponseSettingsEnum.EXPECTED_CONTENT_TYPE));
        if(responseSettings.containsKey(SpecResponseSettingsEnum.VALIDATION_SCHEMA))
            responseSpec.body(matchesJsonSchemaInClasspath((String)responseSettings.get(SpecResponseSettingsEnum.VALIDATION_SCHEMA)));

        return responseSpec;
    }
}
