package data_provider;

import specs.settings.SpecRequestSettingsEnum;
import specs.settings.SpecResponseSettingsEnum;
import global_data.GlobalData;
import global_data.GlobalEnums;
import models.userprofile.SiteTokenUserResponse;
import models.userprofile.UserProfileForm;
import models.userprofile.UserProfile;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Map;
import java.util.stream.Stream;

import static props.Properties.testProperties;

public class UserProfileDataProvider {

    public static Stream<Arguments> getUserValidTokenSuccessArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.CONTENT_TYPE, "application/json"),
                Map.entry(SpecRequestSettingsEnum.ACCEPT, "application/json"),
                Map.entry(SpecRequestSettingsEnum.TOKEN, true),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.usersActMePartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 200),
                Map.entry(SpecResponseSettingsEnum.EXPECTED_CONTENT_TYPE, "application/json"),
                Map.entry(SpecResponseSettingsEnum.VALIDATION_SCHEMA, "schemas/userProfileSchema.json")
        );

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings
                )
        );
    }

    public static Stream<Arguments> getUserNoTokenFailureArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.CONTENT_TYPE, "application/json"),
                Map.entry(SpecRequestSettingsEnum.ACCEPT, "application/json"),
                Map.entry(SpecRequestSettingsEnum.TOKEN, false),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.usersActMePartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 401)
        );

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings, Map.of("error", "Please authenticate.")
                )
        );
    }

    public static Stream<Arguments> patchUserValidTokenEntityFullSuccessArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.CONTENT_TYPE, "application/json"),
                Map.entry(SpecRequestSettingsEnum.ACCEPT, "application/json"),
                Map.entry(SpecRequestSettingsEnum.TOKEN, true),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.usersActMePartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 200),
                Map.entry(SpecResponseSettingsEnum.EXPECTED_CONTENT_TYPE, "application/json"),
                Map.entry(SpecResponseSettingsEnum.VALIDATION_SCHEMA, "schemas/userProfileSchema.json")
        );

        UserProfile previousUserProfile = ((SiteTokenUserResponse) GlobalData.getItem(GlobalEnums.ADD_USER_RESPONSE)).getUser();
        UserProfileForm patchUserProfileForm = new UserProfileForm("updated", "updated_debile", "debil_updated@fake.com", "updated_pass12345678");
        previousUserProfile.setFirstName(patchUserProfileForm.getFirstName());
        previousUserProfile.setLastName(patchUserProfileForm.getLastName());
        previousUserProfile.setEmail(patchUserProfileForm.getEmail());

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings, patchUserProfileForm, previousUserProfile
                )
        );
    }

    public static Stream<Arguments> patchUserValidTokenEntityHasNullFieldsFailureArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.CONTENT_TYPE, "application/json"),
                Map.entry(SpecRequestSettingsEnum.ACCEPT, "application/json"),
                Map.entry(SpecRequestSettingsEnum.TOKEN, true),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.usersActMePartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 400),
                Map.entry(SpecResponseSettingsEnum.EXPECTED_CONTENT_TYPE, "application/json"),
                Map.entry(SpecResponseSettingsEnum.VALIDATION_SCHEMA, "schemas/userProfileSchema.json")
        );


        UserProfileForm patchUserProfileForm = new UserProfileForm();
        patchUserProfileForm.setFirstName("updated");

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings, patchUserProfileForm, Map.of("_message", "User validation failed")
                )
        );
    }

    public static Stream<Arguments> patchUserNoTokenUnauthorizedAndFailureArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.CONTENT_TYPE, "application/json"),
                Map.entry(SpecRequestSettingsEnum.ACCEPT, "application/json"),
                Map.entry(SpecRequestSettingsEnum.TOKEN, false),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.usersActMePartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 401)
        );

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings, Map.of("error", "Please authenticate.")
                )
        );
    }

    public static Stream<Arguments> logoutUserValidTokenSuccessArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.TOKEN, true),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.logoutPartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 200)
        );

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings
                )
        );
    }

    public static Stream<Arguments> logoutUserNoTokenUnauthorizedAndFailureArgs(){

        Map<SpecRequestSettingsEnum, Object> requestSettings = Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.TOKEN, false),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.logoutPartUrl())
        );
        Map<SpecResponseSettingsEnum, Object> responseSettings = Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 401)
        );

        return Stream.of(
                Arguments.of(
                        requestSettings, responseSettings, Map.of("error", "Please authenticate.")
                )
        );
    }
}
