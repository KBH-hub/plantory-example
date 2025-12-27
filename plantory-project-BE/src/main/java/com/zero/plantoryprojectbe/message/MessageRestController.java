package com.zero.plantoryprojectbe.message;

import com.zero.plantoryprojectbe.global.plantoryEnum.BoxType;
import com.zero.plantoryprojectbe.global.plantoryEnum.TargetType;
import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.message.dto.MessageListResponse;
import com.zero.plantoryprojectbe.message.dto.MessageRequest;
import com.zero.plantoryprojectbe.message.dto.MessageResponse;
import com.zero.plantoryprojectbe.message.dto.MessageSearchRequest;
import com.zero.plantoryprojectbe.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
@Tag(name = "Message", description = "쪽지 API")
public class MessageRestController {

    private final MessageService messageService;

    @Operation(summary = "쪽지 목록 조회", description = "받은함/보낸함 등 박스 타입 기준으로 쪽지 목록을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/{boxType}")
    public ResponseEntity<List<MessageListResponse>> getMessageList(
            @AuthenticationPrincipal MemberPrincipal principal,
            @Parameter(description = "쪽지함 타입", example = "RECEIVED")
            @PathVariable BoxType boxType,
            @Parameter(description = "대상 타입", example = "SHARING")
            @RequestParam(required = false) TargetType targetType,
            @Parameter(description = "제목 검색", example = "문의")
            @RequestParam(required = false) String title,
            @Parameter(description = "조회 시작 위치", example = "0")
            @RequestParam int offset,
            @Parameter(description = "조회 개수", example = "10")
            @RequestParam int limit
    ) {
        MessageSearchRequest req = new MessageSearchRequest(
                principal.getMemberId(),
                boxType,
                targetType,
                title,
                offset,
                limit
        );
        return ResponseEntity.ok().body(messageService.getMessageList(req));
    }

    @Operation(summary = "쪽지 삭제", description = "쪽지 ID 목록을 받아 쪽지를 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping("/deleteMessages")
    public ResponseEntity<Map<String, String>> deleteMessages(
            @RequestBody List<Long> messageIds
    ) {
        int result = messageService.removeMessages(messageIds);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "delete message success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "delete message fail"));
    }

    @Operation(summary = "보낸 쪽지 삭제", description = "보낸 쪽지함에서 쪽지 ID 목록을 받아 삭제합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping("/deleteSenderMessages")
    public ResponseEntity<Map<String, String>> deleteSenderMessages(
            @RequestBody List<Long> messageIds
    ) {
        int result = messageService.removeSenderMessages(messageIds);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "delete message success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "delete message fail"));
    }

    @Operation(summary = "쪽지 상세 조회", description = "쪽지 ID로 상세 내용을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "쪽지 없음"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @GetMapping("/detail/{messageId}")
    public ResponseEntity<MessageResponse> getMessage(
            @Parameter(description = "쪽지 ID", example = "1")
            @PathVariable Long messageId,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        MessageResponse result = messageService.findMessageDetail(
                messageId,
                principal.getMemberId()
        );
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "쪽지 전송", description = "쪽지를 전송합니다. senderId는 로그인 사용자로 서버에서 세팅됩니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전송 성공"),
            @ApiResponse(responseCode = "400", description = "전송 실패"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PostMapping("/messageRegist")
    public ResponseEntity<Map<String, String>> registerMessage(
            @RequestBody MessageRequest messageRequest,
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        messageRequest.setSenderId(principal.getMemberId());
        int result = messageService.registerMessage(messageRequest);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "regist message success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "regist message fail"));
    }
}