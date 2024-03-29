package BuildMyApplication.com.services;

import BuildMyApplication.com.entities.RefreshedToken;
import BuildMyApplication.com.entities.UserInfo;
import BuildMyApplication.com.repository.RefreshTokenRepo;
import BuildMyApplication.com.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServices {

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    UserRepos userRepos;

    public RefreshedToken createRefreshedToken(String username){
        UserInfo userInfoExtracted = userRepos.findByUsername(username);

        RefreshedToken refreshedToken = RefreshedToken
                .builder().userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();

        return refreshTokenRepo.save(refreshedToken);
    }


    public RefreshedToken verifyExpiration(RefreshedToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken()+"Refreshed Token Is Expired,Please Make A new Login");
        }
        return token;
    }

    public Optional<RefreshedToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }
}
