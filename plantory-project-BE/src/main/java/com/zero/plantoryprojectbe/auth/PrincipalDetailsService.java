package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.repository.User;
import com.zero.plantoryprojectbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service //서비스는 template 이므로 반드시 사용 필요
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 유저의 정보를 로딩하는게 목적
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // callback 패턴 (IoC랑은 다른 개념)
        User user = userRepository.findByUsername(username);
        if (user != null)
            return new PrincipalDetails(user); // 개발자가 결정할 수 밖에 없음 (email or username ...)
            //new 이유 - 들어오는 id 마다 정보가 다르므로 new 할 수 밖에 없음
        return null; // 들어온게 없으면 null 반환
    }
}
