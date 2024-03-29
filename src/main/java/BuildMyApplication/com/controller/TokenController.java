package BuildMyApplication.com.controller;


import BuildMyApplication.com.entities.RefreshedToken;
import BuildMyApplication.com.request.AuthRequestDTO;
import BuildMyApplication.com.request.RefreshTokenReq;
import BuildMyApplication.com.response.JwtResponseDTO;
import BuildMyApplication.com.services.JwtServices;
import BuildMyApplication.com.services.RefreshTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenServices refreshTokenServices;

    @Autowired
    private JwtServices jwtServices;

    @PostMapping("auth/v1/login")
    public ResponseEntity AuthenticatedAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        try {
            Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),authRequestDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                RefreshedToken refreshedToken = refreshTokenServices.createRefreshedToken(authRequestDTO.getUsername());
                return new ResponseEntity<>(JwtResponseDTO.builder()
                        .accessToken("")
                        .token("")
                        .build(), HttpStatus.OK);
            }
        }catch (Exception ex){
            return new ResponseEntity<>("Problem in Token controller "+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

     return null;
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDTO refreshToken (@RequestBody RefreshTokenReq refreshTokenReq){
        return refreshTokenServices.findByToken(refreshTokenReq.getToken())
                .map(refreshTokenServices::verifyExpiration)
                .map(RefreshedToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtServices.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenReq.getToken())
                            .build();
                }).orElseThrow(()->new RuntimeException("Refresh Token is not in db"));
    }
}
