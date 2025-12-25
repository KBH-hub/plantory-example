package com.zero.plantoryprojectbe.question.service;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import com.zero.plantoryprojectbe.image.ImageMapper;
import com.zero.plantoryprojectbe.question.QuestionMapper;
import com.zero.plantoryprojectbe.question.dto.QuestionListPageResponse;
import com.zero.plantoryprojectbe.question.dto.SelectAnswerListResponse;
import com.zero.plantoryprojectbe.question.dto.SelectQuestionDetailResponse;
import com.zero.plantoryprojectbe.question.dto.SelectQuestionListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionReadServiceImpl implements QuestionReadService {
    private final QuestionMapper questionMapper;
    private final ImageMapper imageMapper;

    @Override
    public QuestionListPageResponse getQuestionList(String keyword, int page, int size) {

        int limit = size;
        int offset = (page - 1) * size;

        List<SelectQuestionListResponse> list = questionMapper.selectQuestionList(keyword, limit, offset);

        int totalCount = questionMapper.selectQuestionTotalCount(keyword);

        return QuestionListPageResponse.builder()
                .list(list)
                .totalCount(totalCount)
                .page(page)
                .size(size)
                .build();
    }

    @Override
    public SelectQuestionDetailResponse getQuestionDetail(Long questionId) {

        SelectQuestionDetailResponse vo = questionMapper.selectQuestionDetail(questionId);

        if (vo == null) {
            throw new IllegalArgumentException("존재하지 않는 질문입니다.");
        }

        List<ImageDTO> images = imageMapper.selectImagesByTarget(ImageTargetType.QUESTION, questionId);

        vo.setImages(images);

        return vo;
    }

    @Override
    public List<SelectAnswerListResponse> getAnswerList(Long questionId) {
        return questionMapper.selectQuestionAnswers(questionId);
    }
}
