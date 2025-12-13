package com.zero.plantoryprojectbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    //google login email 확인 필수
    User findByEmail(String email);
}
