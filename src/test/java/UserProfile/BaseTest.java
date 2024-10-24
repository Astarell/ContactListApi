package UserProfile;

import specs.settings.SpecRequestSettingsEnum;
import specs.settings.SpecResponseSettingsEnum;
import global_data.GlobalData;
import global_data.GlobalEnums;
import models.auth.UserCredentials;
import models.userprofile.SiteTokenUserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static specs.BaseSpec.*;
import static io.restassured.RestAssured.given;
import static steps.userprofile.ApiRequestQuery.doPost;

public class BaseTest {

    @BeforeAll
    @DisplayName("Authorize user and get token")
    public static void userGetToken(){
        UserCredentials credentials = (UserCredentials)GlobalData.getItem(GlobalEnums.TEST_USER_CREDENTIALS);
        installSpec(customRequestSpec((Map<SpecRequestSettingsEnum, Object>)GlobalData.getItem(GlobalEnums.TEST_USER_REQUEST_SETTINGS)),
                customResponseSpec((Map<SpecResponseSettingsEnum, Object>)GlobalData.getItem(GlobalEnums.TEST_USER_RESPONSE_SETTINGS)));

        SiteTokenUserResponse siteTokenUserResponse = doPost(credentials).as(SiteTokenUserResponse.class);

        GlobalData.putItem(GlobalEnums.ADD_USER_RESPONSE, siteTokenUserResponse);
        removeSpec();
    }
}
