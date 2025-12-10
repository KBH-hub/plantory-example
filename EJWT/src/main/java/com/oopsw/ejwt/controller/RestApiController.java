package com.oopsw.ejwt.controller;

import com.oopsw.ejwt.service.UserService;
import com.oopsw.ejwt.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RestApiController {

    @Autowired
    private UserService userService;

    @GetMapping("main")
    public Map<String, String> main() {
        return Map.of("message", "MainData");
    }

    @PostMapping("join")
    public ResponseEntity<Map<String, String>> join(@RequestBody UserVO userVO) {
//        System.out.println(userVO);
        if(userService.join(userVO))
            return ResponseEntity.ok(Map.of("message", "Join OK"));
        return ResponseEntity.badRequest().body(Map.of("message", "error"));
    }

    @GetMapping("/api/jwt/user")
    public Map<String, String> getJwtUser(Authentication authentication) {
        return Map.of("message", "user = "+authentication);
    }
    @GetMapping("/api/jwt/manager")
    public Map<String, String> getJwtManager(Authentication authentication) {
        return Map.of("message", "manager = "+authentication);
    }
    @GetMapping("/api/jwt/admin")
    public Map<String, String> getJwtAdmin(Authentication authentication) {
        return Map.of("message", "admin = "+authentication);
    }
}
