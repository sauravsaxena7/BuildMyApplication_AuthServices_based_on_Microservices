package BuildMyApplication.com.repository;

import BuildMyApplication.com.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepos extends CrudRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);
}
