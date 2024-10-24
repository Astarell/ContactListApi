package props;

import org.aeonbits.owner.Config;
import org.checkerframework.checker.units.qual.K;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources(
        "classpath:contactlistapi.properties"
)
public interface TestProperties extends Config {

    @Key("base_url")
    String baseUrl();

    @Key("logout_part_url")
    String logoutPartUrl();

    @Key("login_part_url")
    String loginPartUrl();

    @Key("users_actions_part_url")
    String usersActPartUrl();

    @Key("users_actions_me_part_url")
    String usersActMePartUrl();

    @Key("test_user_email")
    String testUserEmail();

    @Key("test_user_pass")
    String testUserPass();
}
