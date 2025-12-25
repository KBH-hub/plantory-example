package com.zero.plantoryprojectbe.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그인 요청")
public class LoginRequest {

    @Schema(description = "회원 아이디", example = "soo8848")
    private String membername;

    @Schema(description = "비밀번호", example = "영문자와번호특수기호를섞은비밀번호!")
    private String password;
}
