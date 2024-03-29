package BuildMyApplication.com.models;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto {

    private String username;

    private String firstName;

    private String lastName;

    private String img_url;

    private String poster_url;

    private String password;

    private String phone;

    private String email;
}
