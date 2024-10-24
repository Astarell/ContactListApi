package UserProfile;

import org.junit.jupiter.api.*;
import specs.settings.SpecRequestSettingsEnum;
import specs.settings.SpecResponseSettingsEnum;
import global_data.GlobalData;
import global_data.GlobalEnums;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.userprofile.SiteTokenUserResponse;
import models.userprofile.UserProfileForm;
import models.userprofile.UserProfile;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

import static props.Properties.testProperties;
import static specs.BaseSpec.*;
import static steps.userprofile.ApiRequestQuery.*;
import static steps.userprofile.ApiRequestQuery.doPatch;
import static steps.userprofile.ApiResponseChecks.checkResponseBodyExpectedFields;


public class UserProfileControllerTests {

    @Nested
    class GetTests extends BaseTest {

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#getUserValidTokenSuccessArgs")
        @Epic("User API")
        @Feature("Get")
        @Tags({@Tag("smoke"), @Tag("api")})
        @Story(value = "Positive")
        @DisplayName("Return 200 with valid body and expected fields when getting user valid token")
        public void getUser_WhenGetUser_AndValidTokenPresent_ThenSuccess(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                         Map<SpecResponseSettingsEnum, Object> responseSettings){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            doGet();

            //then
            removeSpec();
        }

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#getUserNoTokenFailureArgs")
        @Epic("User API")
        @Feature("Get")
        @Tags({@Tag("smoke"), @Tag("api")})
        @Story(value = "Negative")
        @DisplayName("Return 401 when perform Get without token/with invalid token")
        public void getUser_WhenGetUser_AndValidTokenNotPresent_ThenUnauthorizedAndFailure(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                                           Map<SpecResponseSettingsEnum, Object> responseSettings,
                                                                                           Map<String, String> expectedFields){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            Response response = doGet();

            //then
            checkResponseBodyExpectedFields(response, expectedFields);
            removeSpec();
        }
    }

    @Nested
    class PatchTests extends BaseTest {

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#patchUserValidTokenEntityFullSuccessArgs")
        @Epic("User API")
        @Feature("Patch")
        @Tags({@Tag("smoke"), @Tag("api")})
        @Story(value = "Positive")
        @DisplayName("Return 200 and perform action if token is valid and patch entity full")
        public void patchUser_WhenPatchUser_AndValidTokenPresent_AndPatchEntityFull_ThenSuccess(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                                                Map<SpecResponseSettingsEnum, Object> responseSettings,
                                                                                                UserProfileForm patchUser,
                                                                                                UserProfile previousUserData){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            UserProfile responseUserData = doPatch(patchUser).as(UserProfile.class);

            //then
            Assertions.assertEquals(previousUserData, responseUserData,
                    "Ожидаемый результат [" + previousUserData.toString() + "] не совпадает с фактическим [" + responseUserData.toString() + "]");

            UserProfile realPrevUserData = ((SiteTokenUserResponse) GlobalData.getItem(GlobalEnums.ADD_USER_RESPONSE)).getUser();
            doRollBackPatch(new UserProfileForm(realPrevUserData.getFirstName(), realPrevUserData.getLastName(), testProperties.testUserEmail(), testProperties.testUserPass()));

            removeSpec();
        }

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#patchUserValidTokenEntityHasNullFieldsFailureArgs")
        @Epic("User API")
        @Feature("Patch")
        @Tags({@Tag("smoke"), @Tag("api")})
        @Story(value = "Negative")
        @DisplayName("Return 400 and NOT perform action if token is valid and patch entity NOT full")
        public void patchUser_WhenPatchUser_AndValidTokenPresent_AndPatchEntityNotFull_ThenFailure_AndValidationFailed(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                                                                       Map<SpecResponseSettingsEnum, Object> responseSettings,
                                                                                                                       UserProfileForm patchUser,
                                                                                                                       Map<String, String> expectedFields){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            Response response = doPatch(patchUser);

            //then
            checkResponseBodyExpectedFields(response, expectedFields);
            removeSpec();
        }

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#patchUserNoTokenUnauthorizedAndFailureArgs")
        @Epic("User API")
        @Feature("Patch")
        @Tags({@Tag("smoke"), @Tag("api")})
        @Story(value = "Negative")
        @DisplayName("Return 401 when perform Patch without token/with invalid token")
        public void patchUser_WhenPatchUser_AndValidTokenNotPresent_ThenUnauthorizedAndFailure(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                                               Map<SpecResponseSettingsEnum, Object> responseSettings,
                                                                                               Map<String, String> expectedFields){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            Response response = doPatch();

            //then
            checkResponseBodyExpectedFields(response, expectedFields);
            removeSpec();
        }
    }

    @Nested
    public class LogOutTests extends BaseTest {

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#logoutUserValidTokenSuccessArgs")
        @Epic("User API")
        @Feature("Logout")
        @Tags({@Tag("smoke"), @Tag("auth")})
        @Story(value = "Positive")
        @DisplayName("Return 200 and logout when perform POST with valid token")
        public void logoutUser_WhenLogOutUser_AndValidTokenPresent_ThenUserLoggedOut_AndSuccess(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                                                Map<SpecResponseSettingsEnum, Object> responseSettings){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            doPost();

            //then
            removeSpec();

        }

        @ParameterizedTest(name = "{displayName}")
        @MethodSource("data_provider.UserProfileDataProvider#logoutUserNoTokenUnauthorizedAndFailureArgs")
        @Epic("User API")
        @Feature("Logout")
        @Tags({@Tag("smoke"), @Tag("auth")})
        @Story(value = "Negative")
        @DisplayName("Return 401 when perform POST without token/with invalid token")
        public void logoutUser_WhenLogOutUser_AndValidTokenNotPresent_ThenFailure(Map<SpecRequestSettingsEnum, Object> requestSettings,
                                                                                  Map<SpecResponseSettingsEnum, Object> responseSettings,
                                                                                  Map<String, String> expectedFields){
            //given
            installSpec(customRequestSpec(requestSettings), customResponseSpec(responseSettings));

            //when
            Response response = doPost();

            //then
            checkResponseBodyExpectedFields(response, expectedFields);
            removeSpec();

        }
    }

}
