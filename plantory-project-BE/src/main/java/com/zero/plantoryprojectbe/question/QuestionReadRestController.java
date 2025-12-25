package com.zero.plantoryprojectbe.question;

import com.zero.plantoryprojectbe.question.dto.QuestionListPageResponse;
import com.zero.plantoryprojectbe.question.dto.SelectAnswerListResponse;
import com.zero.plantoryprojectbe.question.dto.SelectQuestionDetailResponse;
import com.zero.plantoryprojectbe.question.service.QuestionReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Question", description = "질문/답변 조회 API")
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionReadRestController {

    private final QuestionReadService questionReadService;

    @Operation(
            summary = "질문 목록 조회",
            description = "질문 목록을 페이징으로 조회합니다. keyword가 있으면 제목/내용 기준 검색합니다."
    )
    @GetMapping
    public QuestionListPageResponse getQuestionList(
            @Parameter(description = "페이지(1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 사이즈", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "검색 키워드(선택)", example = "물주기")
            @RequestParam(required = false) String keyword
    ) {
        return questionReadService.getQuestionList(keyword, page, size);
    }

    @Operation(
            summary = "질문 상세 조회",
            description = "questionId로 질문 상세를 조회합니다."
    )
    @GetMapping("/{questionId}")
    public SelectQuestionDetailResponse getQuestionDetail(
            @Parameter(description = "질문 ID", example = "100")
            @PathVariable Long questionId
    ) {
        return questionReadService.getQuestionDetail(questionId);
    }

    @Operation(
            summary = "답변 목록 조회",
            description = "questionId에 해당하는 답변 목록을 조회합니다."
    )
    @GetMapping("/{questionId}/answers")
    public List<SelectAnswerListResponse> getAnswerList(
            @Parameter(description = "질문 ID", example = "100")
            @PathVariable Long questionId
    ) {
        return questionReadService.getAnswerList(questionId);
    }
}
