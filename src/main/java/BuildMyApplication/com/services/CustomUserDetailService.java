package BuildMyApplication.com.services;

import BuildMyApplication.com.entities.UserInfo;
import BuildMyApplication.com.entities.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetailService extends UserInfo implements UserDetails {

    private String username;
    private String password;

    Collection<? extends GrantedAuthority> authorities;



    public CustomUserDetailService(UserInfo byUsername){
        this.username=byUsername.getUsername();
        this.password=byUsername.getPassword();
        List<GrantedAuthority> auths= new ArrayList<>();

        for(UserRoles role:byUsername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getClass().getName().toUpperCase()));
        }

        this.authorities=auths;
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


}
