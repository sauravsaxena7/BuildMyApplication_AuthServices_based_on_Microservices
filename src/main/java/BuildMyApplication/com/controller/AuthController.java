package BuildMyApplication.com.controller;


import BuildMyApplication.com.entities.RefreshedToken;
import BuildMyApplication.com.models.UserInfoDto;
import BuildMyApplication.com.response.JwtResponseDTO;
import BuildMyApplication.com.services.JwtServices;
import BuildMyApplication.com.services.RefreshTokenServices;
import BuildMyApplication.com.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private RefreshTokenServices refreshTokenServices;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @PostMapping("auth/v1/signup")
    public ResponseEntity signUp(@RequestBody UserInfoDto userInfoDto){
        try{

            Boolean isSignUped = userDetailsServiceImpl.signUpUser(userInfoDto);

            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("You are already Associated with us!", HttpStatus.BAD_REQUEST);
            }

            RefreshedToken refreshedToken = refreshTokenServices.createRefreshedToken(userInfoDto.getUsername());
            String token = jwtServices.GenerateToken(userInfoDto.getUsername());

            return new ResponseEntity<>(
                    JwtResponseDTO
                            .builder()
                            .accessToken(token)
                            .token(refreshedToken.getToken()).build(),HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<>("Excepton in userService "+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
