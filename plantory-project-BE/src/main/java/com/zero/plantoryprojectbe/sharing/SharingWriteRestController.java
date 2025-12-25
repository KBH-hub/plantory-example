package com.zero.plantoryprojectbe.sharing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.sharing.dto.CommentRequest;
import com.zero.plantoryprojectbe.sharing.dto.SharingRequest;
import com.zero.plantoryprojectbe.sharing.service.SharingWriteService;
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

@Tag(name = "SharingWrite", description = "나눔 등록/수정/삭제 및 댓글/관심 API")
@RestController
@RequestMapping("/api/sharings")
@RequiredArgsConstructor
public class SharingWriteRestController {

    private final SharingWriteService sharingWriteService;
    private final SharingMapper sharingMapper;

    @Operation(
            summary = "나눔 등록",
            description = "나눔 게시글을 등록합니다. files는 선택입니다. (인증 필요)"
    )
    @PostMapping
    public ResponseEntity<?> createSharing(
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Parameter(description = "나눔 등록 폼데이터", example = "title/content 등")
            @ModelAttribute SharingRequest request,
            @Parameter(description = "첨부 이미지(선택)", example = "files")
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        request.setMemberId(memberDetail.memberResponse().getMemberId());
        Long sharingId = sharingWriteService.registerSharing(request, files);
        return ResponseEntity.ok(sharingId);
    }

    @Operation(
            summary = "나눔 수정",
            description = "나눔 게시글을 수정합니다. deletedImageIds는 JSON 배열 문자열입니다. (인증 필요)"
    )
    @PutMapping("/{sharingId}")
    public ResponseEntity<?> updateSharing(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(description = "나눔 수정 폼데이터", example = "title/content 등")
            @ModelAttribute SharingRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Parameter(description = "추가 이미지(선택)", example = "files")
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {

        request.setSharingId(sharingId);
        request.setMemberId(memberDetail.memberResponse().getMemberId());

        if (request.getDeletedImageIds() != null && !request.getDeletedImageIds().isBlank()) {
            ObjectMapper mapper = new ObjectMapper();
            List<Long> ids = mapper.readValue(request.getDeletedImageIds(), new TypeReference<List<Long>>() {});
            request.setDeletedImageIdList(ids);
        }

        boolean result = sharingWriteService.updateSharing(request, files);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "나눔 삭제",
            description = "나눔 게시글을 삭제합니다. 작성자만 삭제 가능합니다. (인증 필요)"
    )
    @DeleteMapping("/{sharingId}")
    public ResponseEntity<?> deleteSharing(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.deleteSharing(sharingId, memberId);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "관심 등록",
            description = "나눔 게시글에 관심(찜)을 등록합니다. (인증 필요)"
    )
    @PostMapping("/{sharingId}/interest")
    public ResponseEntity<?> addInterest(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.addInterest(memberId, sharingId);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "관심 취소",
            description = "나눔 게시글 관심(찜)을 취소합니다. (인증 필요)"
    )
    @DeleteMapping("/{sharingId}/interest")
    public ResponseEntity<?> removeInterest(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.removeInterest(memberId, sharingId);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "댓글 등록",
            description = "나눔 게시글에 댓글을 등록합니다. (인증 필요)"
    )
    @PostMapping("/{sharingId}/comments")
    public ResponseEntity<?> addComment(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestBody CommentRequest request
    ) {
        Long loginMemberId = memberDetail.memberResponse().getMemberId();
        request.setWriterId(loginMemberId);
        request.setSharingId(sharingId);

        boolean result = sharingWriteService.addComment(request);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "댓글 수정",
            description = "댓글을 수정합니다. 작성자만 수정 가능합니다. (인증 필요)"
    )
    @PutMapping("/{sharingId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(description = "댓글 ID", example = "300")
            @PathVariable Long commentId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestBody CommentRequest request
    ) {
        request.setCommentId(commentId);
        request.setWriterId(memberDetail.memberResponse().getMemberId());
        request.setSharingId(sharingId);

        boolean result = sharingWriteService.updateComment(request);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제합니다. 작성자만 삭제 가능합니다. (인증 필요)"
    )
    @DeleteMapping("/{sharingId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @Parameter(description = "댓글 ID", example = "300")
            @PathVariable Long commentId,
            @Parameter(description = "나눔 ID", example = "100")
            @PathVariable Long sharingId,
            @Parameter(hidden = true)
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long loginMemberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.deleteComment(commentId, sharingId, loginMemberId);
        return ResponseEntity.ok(result);
    }
}
