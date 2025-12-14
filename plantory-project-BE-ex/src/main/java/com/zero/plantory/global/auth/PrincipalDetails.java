package com.zero.plantory.global.auth;

import com.zero.plantory.global.repository.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {
    private User user; // 들어왔다는 건 검증은 끝났다는 의미. 따라서 id, pw, role 등 정보 체크
    public PrincipalDetails(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 인가 정보
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); //권한 여러개이나 중복될일 X
    authorities.add(new GrantedAuthority() {
        @Override
        public String getAuthority() {
            return user.getRole();
        }
    });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // 이메일로 로그인하고싶으면 user.getEmail()
    }

}
