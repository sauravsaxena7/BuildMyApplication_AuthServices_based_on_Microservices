package BuildMyApplication.com.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {
    private String username;
    private String password;

    private String phone;

    private String email;
}
