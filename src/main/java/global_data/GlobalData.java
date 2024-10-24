package global_data;

import specs.settings.SpecRequestSettingsEnum;
import specs.settings.SpecResponseSettingsEnum;
import models.auth.UserCredentials;

import java.util.HashMap;
import java.util.Map;

import static props.Properties.testProperties;

public class GlobalData {

    private static Map<GlobalEnums, Object> globDataMap = new HashMap<>(){{
        put(GlobalEnums.TEST_USER_CREDENTIALS, new UserCredentials(testProperties.testUserEmail(), testProperties.testUserPass()));
        put(GlobalEnums.TEST_USER_REQUEST_SETTINGS, Map.ofEntries(
                Map.entry(SpecRequestSettingsEnum.CONTENT_TYPE, "application/json"),
                Map.entry(SpecRequestSettingsEnum.ACCEPT, "application/json"),
                Map.entry(SpecRequestSettingsEnum.BASE_URI, testProperties.baseUrl()),
                Map.entry(SpecRequestSettingsEnum.PATH_URL, testProperties.loginPartUrl())
        ));
        put(GlobalEnums.TEST_USER_RESPONSE_SETTINGS, Map.ofEntries(
                Map.entry(SpecResponseSettingsEnum.STATUS_CODE, 200),
                Map.entry(SpecResponseSettingsEnum.EXPECTED_CONTENT_TYPE, "application/json"),
                Map.entry(SpecResponseSettingsEnum.VALIDATION_SCHEMA, "schemas/addUserResponseSchema.json")
        ));
    }};

    public static void putItem(GlobalEnums itemEnum, Object item){
        globDataMap.put(itemEnum, item);
    }

    public static void removeItem(GlobalEnums itemEnum){
        globDataMap.remove(itemEnum);
    }

    public static Object getItem(GlobalEnums itemEnum){
        return globDataMap.get(itemEnum);
    }
}
