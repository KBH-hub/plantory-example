package com.zero.plantoryprojectbe.question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.plantoryprojectbe.global.security.MemberPrincipal;
import com.zero.plantoryprojectbe.question.dto.AnswerRequest;
import com.zero.plantoryprojectbe.question.dto.QuestionRequest;
import com.zero.plantoryprojectbe.question.service.QuestionWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Question", description = "질문/답변 등록/수정/삭제 API")
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionWriteRestController {

    private final QuestionWriteService questionWriteService;

    @Operation(
            summary = "질문 등록",
            description = "질문을 등록합니다. 이미지 파일은 선택입니다. (인증 필요)"
    )
    @PostMapping
    public ResponseEntity<?> createQuestion(
            @Parameter(description = "질문 등록 폼데이터", example = "title/content 등")
            @ModelAttribute QuestionRequest request,
            @Parameter(description = "첨부 이미지(선택)", example = "files")
            @RequestParam(value = "files", required = false) List<MultipartFile> images,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal
    ) throws IOException {
        request.setMemberId(principal.getMemberId());
        Long questionId = questionWriteService.registerQuestion(request, images);
        return ResponseEntity.ok(questionId);
    }

    @Operation(
            summary = "질문 수정",
            description = "질문을 수정합니다. 삭제할 이미지 ID 목록은 deletedImageIds(JSON 배열 문자열)로 전달합니다. (인증 필요)"
    )
    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(
            @Parameter(description = "질문 ID", example = "100")
            @PathVariable Long questionId,
            @Parameter(description = "질문 수정 폼데이터", example = "title/content 등")
            @ModelAttribute QuestionRequest request,
            @Parameter(description = "추가 이미지(선택)", example = "files")
            @RequestParam(value = "files", required = false) List<MultipartFile> newImages,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal
    ) throws IOException {

        request.setQuestionId(questionId);
        Long loginMemberId = principal.getMemberId();
        request.setMemberId(loginMemberId);

        if (request.getDeletedImageIds() != null && !request.getDeletedImageIds().isBlank()) {
            ObjectMapper mapper = new ObjectMapper();
            List<Long> ids = mapper.readValue(request.getDeletedImageIds(), new TypeReference<List<Long>>() {});
            request.setDeletedImageIdList(ids);
        }

        boolean result = questionWriteService.updateQuestion(request, loginMemberId, newImages);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "질문 삭제",
            description = "질문을 삭제합니다. 작성자만 삭제 가능합니다. (인증 필요)"
    )
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(
            @Parameter(description = "질문 ID", example = "100")
            @PathVariable Long questionId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        Long memberId = principal.getMemberId();
        boolean result = questionWriteService.deleteQuestion(questionId, memberId);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "답변 등록",
            description = "questionId에 답변을 등록합니다. (인증 필요)"
    )
    @PostMapping("/{questionId}/answers")
    public ResponseEntity<?> addAnswer(
            @Parameter(description = "질문 ID", example = "100")
            @PathVariable Long questionId,
            @RequestBody AnswerRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        request.setQuestionId(questionId);
        request.setWriterId(principal.getMemberId());
        boolean result = questionWriteService.addAnswer(request);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "답변 수정",
            description = "답변을 수정합니다. 작성자만 수정 가능합니다. (인증 필요)"
    )
    @PutMapping("/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(
            @Parameter(description = "답변 ID", example = "200")
            @PathVariable Long answerId,
            @RequestBody AnswerRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        request.setAnswerId(answerId);
        Long loginId = principal.getMemberId();
        request.setWriterId(loginId);
        boolean result = questionWriteService.updateAnswer(request, loginId);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "답변 삭제",
            description = "답변을 삭제합니다. 작성자만 삭제 가능합니다. (인증 필요)"
    )
    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(
            @Parameter(description = "답변 ID", example = "200")
            @PathVariable Long answerId,
            @Parameter(description = "삭제 요청 바디", example = "answerId/writerId는 서버에서 세팅")
            @RequestBody AnswerRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberPrincipal principal
    ) {
        Long loginId = principal.getMemberId();
        request.setAnswerId(answerId);
        request.setWriterId(loginId);
        boolean result = questionWriteService.deleteAnswer(request, loginId);
        return ResponseEntity.ok(result);
    }
}
