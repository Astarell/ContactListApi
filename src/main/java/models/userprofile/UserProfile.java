package models.userprofile;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserProfile {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String views;

    @JsonCreator
    public UserProfile(@JsonProperty("_id") String id,
                       @JsonProperty("firstName") String firstName,
                       @JsonProperty("lastName") String lastName,
                       @JsonProperty("email") String email,
                       @JsonProperty("__v") String views){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.views = views;
    }
}
