package BuildMyApplication.com.repository;

import BuildMyApplication.com.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepos extends CrudRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);
}
