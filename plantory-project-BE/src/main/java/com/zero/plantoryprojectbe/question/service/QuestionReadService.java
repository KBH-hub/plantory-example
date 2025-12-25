package com.zero.plantoryprojectbe.question.service;

import com.zero.plantoryprojectbe.question.dto.QuestionListPageResponse;
import com.zero.plantoryprojectbe.question.dto.SelectAnswerListResponse;
import com.zero.plantoryprojectbe.question.dto.SelectQuestionDetailResponse;

import java.util.List;

public interface QuestionReadService {
    QuestionListPageResponse getQuestionList(String keyword, int page, int size);
    SelectQuestionDetailResponse getQuestionDetail(Long questionId);
    List<SelectAnswerListResponse> getAnswerList(Long questionId);
}
