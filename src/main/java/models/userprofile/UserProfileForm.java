package models.userprofile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
