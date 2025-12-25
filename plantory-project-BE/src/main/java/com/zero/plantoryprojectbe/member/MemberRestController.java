package com.zero.plantoryprojectbe.member;

import com.zero.plantoryprojectbe.member.service.MemberService;
import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
@Tag(name = "Member", description = "회원 가입 및 중복 체크 API")
public class MemberRestController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "신규 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 오류"),
            @ApiResponse(responseCode = "409", description = "중복된 회원 정보")
    })
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(
            @RequestBody MemberSignUpRequest request
    ) {
        memberService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이디 중복 확인", description = "회원 아이디(membername) 중복 여부를 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/checkMembername")
    public ResponseEntity<Map<String, Boolean>> checkMembername(
            @Parameter(description = "회원 아이디", example = "soo8848")
            @RequestParam String membername
    ) {
        boolean isDuplicate = memberService.isDuplicateMembername(membername);
        return ResponseEntity.ok(Map.of("exists", isDuplicate));
    }

    @Operation(summary = "닉네임 중복 확인", description = "회원 닉네임 중복 여부를 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/checkNickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(
            @Parameter(description = "닉네임", example = "제육볶음밥")
            @RequestParam String nickname
    ) {
        boolean isDuplicate = memberService.isDuplicateNickname(nickname);
        return ResponseEntity.ok(Map.of("exists", isDuplicate));
    }
}