package BuildMyApplication.com.repository;


import BuildMyApplication.com.entities.RefreshedToken;
import BuildMyApplication.com.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshedToken,Integer> {


    //SELECT * FROM TOKEN WHERE TOKEN=TOKEN1
    Optional<RefreshedToken> findByToken(String Token);

//    UserInfo findUsernameAndPassword(String username,String password);





}
