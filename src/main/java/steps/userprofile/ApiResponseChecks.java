package steps.userprofile;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class ApiResponseChecks {

    @Step("Checks response body contains expected fields [{expectedFields}]")
    public static void checkResponseBodyExpectedFields(Response response, Map<String, String> expectedFields){
        JsonPath jsonPath = new JsonPath(response.asString());

        for(String key : expectedFields.keySet())
            Assertions.assertEquals(jsonPath.get(key), expectedFields.get(key));
    }
}
