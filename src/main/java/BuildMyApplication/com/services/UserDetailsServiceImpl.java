package BuildMyApplication.com.services;

import BuildMyApplication.com.entities.UserInfo;
import BuildMyApplication.com.models.UserInfoDto;
import BuildMyApplication.com.repository.UserRepos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;


@Data
@Component
@AllArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private final UserRepos userRepos;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userRepos.findByUsername(username);

        if(userInfo==null){
            throw new UsernameNotFoundException("USER NOT FOUND...!!!");
        }
        return new CustomUserDetailService(userInfo);
    }

    public UserInfo checkIfUserAlredyExist(UserInfoDto userInfoDto){
        return userRepos.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto){
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        if(Objects.nonNull(checkIfUserAlredyExist(userInfoDto))){
            return false;
        }

        String userId = UUID.randomUUID().toString();
        userRepos.save(
                UserInfo.builder()
                        .userId(userId)
                        .roles(new HashSet<>())
                        .password(userInfoDto.getPassword())
                        .email(userInfoDto.getEmail())
                        .img_url(userInfoDto.getImg_url())
                        .poster_url(userInfoDto.getPoster_url())
                        .phone(userInfoDto.getPhone())
                        .firstName(userInfoDto.getFirstName())
                        .lastName(userInfoDto.getLastName())
                        .username(userInfoDto.getUsername())
                        .build()
        );

        return true;


    }
}
