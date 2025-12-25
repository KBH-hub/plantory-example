package com.zero.plantoryprojectbe.global.security;

import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record MemberDetail(MemberResponse memberResponse) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + memberResponse.getRole().name()));
    }


    @Override
    public String getPassword() {
        return memberResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return memberResponse.getMembername();
    }

}
