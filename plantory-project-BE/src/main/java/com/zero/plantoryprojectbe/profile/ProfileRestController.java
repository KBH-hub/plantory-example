package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.image.service.ImageService;
import com.zero.plantoryprojectbe.profile.dto.PasswordChangeRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import com.zero.plantoryprojectbe.profile.dto.PublicProfileResponse;
import com.zero.plantoryprojectbe.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Tag(name = "Profile", description = "프로필 API")
public class ProfileRestController {

    private final ProfileService profileService;
    private final ImageService imageService;

    @Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필 정보를 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/me")
    public ResponseEntity<ProfileInfoResponse> getProfile(
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        return ResponseEntity.ok(profileService.getProfileInfo(principal.getMemberId()));
    }

    @Operation(summary = "타인 프로필 조회", description = "회원 ID로 공개 프로필 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/publicProfile/{memberId}")
    public ResponseEntity<PublicProfileResponse> getPublicProfile(
            @Parameter(description = "회원 ID", example = "8")
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(profileService.getPublicProfile(memberId));
    }

    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호 확인 후 새 비밀번호로 변경합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 처리 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal MemberPrincipal principal,
            @RequestBody PasswordChangeRequest req
    ) {
        boolean success = profileService.changePassword(
                principal.getMemberId(),
                req.getOldPassword(),
                req.getNewPassword()
        );
        return ResponseEntity.ok(Map.of("success", success));
    }

    @Operation(summary = "회원 탈퇴", description = "로그인한 사용자를 소프트 삭제 처리합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping("/withdraw")
    public ResponseEntity<?> softWithdraw(
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        Long memberId = principal.getMemberId();
        profileService.deleteMemberById(memberId);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }

    @Operation(summary = "프로필 수정", description = "로그인한 사용자의 프로필 정보를 수정합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal MemberPrincipal principal,
            @RequestBody ProfileUpdateRequest profileUpdateRequest
    ) {
        Long memberId = principal.getMemberId();
        profileUpdateRequest.setMemberId(memberId);
        profileService.updateProfileInfo(profileUpdateRequest);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "프로필 사진 업로드", description = "프로필 사진을 업로드하고 imageUrl을 반환합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "업로드 성공"),
            @ApiResponse(responseCode = "400", description = "업로드 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping("/picture")
    public Map<String, Object> uploadProfile(
            @AuthenticationPrincipal MemberPrincipal principal,
            @Parameter(description = "프로필 이미지 파일", required = true)
            @RequestParam("profileImage") MultipartFile file
    ) throws IOException {
        Long memberId = principal.getMemberId();
        String imageUrl = imageService.uploadProfileImage(memberId, file);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("imageUrl", imageUrl);
        return response;
    }

    @Operation(summary = "프로필 사진 조회", description = "회원 ID로 프로필 사진 URL을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/picture")
    public Map<String, Object> getProfileImage(
            @Parameter(description = "회원 ID", example = "8")
            @RequestParam Long memberId
    ) {
        String imageUrl = imageService.getProfileImageUrl(memberId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("imageUrl", imageUrl);
        return response;
    }
}
