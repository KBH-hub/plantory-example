package com.zero.plantoryprojectbe.service;

import com.zero.plantoryprojectbe.repository.User;
import com.zero.plantoryprojectbe.repository.UserRepository;
import com.zero.plantoryprojectbe.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Autowired //메모리에 올라가 있는 것 가져와서 사용
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //회원 가입 (DB에서 유니크 값을 체크하지 않으므로 서비스에서 체크해야 함. 따라서 boolean값 반환)
    public boolean join(UserVO userVO){
        //입력 받은 데이터 중복 여부 확인
        if(userRepository.findByUsername(userVO.getUsername())!=null){
            return false;
        }
        return  userRepository.save(
                User.builder()
                        .username(userVO.getUsername())
                        .email(userVO.getEmail())
                        .password(bCryptPasswordEncoder.encode(userVO.getPassword()))
                        .role("ROLE_USER")
                        .build()) != null;
    }
}
