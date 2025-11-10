package dxw.soup.backend.soupserver.domain.questionset.facade;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetCreateRequest;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetDetailResponse;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetFindAllResponse;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetFindAllResponse.QuestionSetDto;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetItemDto;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import dxw.soup.backend.soupserver.domain.questionset.exception.QuestionSetErrorCode;
import dxw.soup.backend.soupserver.domain.questionset.service.QuestionSetService;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionSetFacade {
    private final QuestionService questionService;
    private final QuestionSetService questionSetService;
    private final UserService userService;

    @Transactional
    public QuestionSetDetailResponse createQuestionSet(Long userId, QuestionSetCreateRequest request) {
        User user = userService.findById(userId);
        List<Question> questions = questionService.getAllByIds(request.questionIds());
        QuestionSet questionSet = questionSetService.createQuestionSet(user, questions.size());
        List<QuestionSetItemDto> questionSetItemDtos = questionSetService.createQuestionSetItems(questionSet, questions)
                .stream()
                .map(QuestionSetItemDto::from)
                .toList();

        return QuestionSetDetailResponse.of(questionSet, questionSetItemDtos);
    }

    public QuestionSetFindAllResponse getAllQuestionSetsByUser(Long userId) {
        User user = userService.findById(userId);
        List<QuestionSetDto> questionSetDtos = questionSetService.getAllQuestionSetsByUser(user)
                .stream()
                .map(QuestionSetDto::from)
                .toList();

        return QuestionSetFindAllResponse.of(questionSetDtos);
    }

    public QuestionSetDetailResponse getQuestionSetById(Long userId, Long questionSetId) {
        User user = userService.findById(userId);
        QuestionSet questionSet = questionSetService.findById(questionSetId);

        validateQuestionSetUser(user, questionSet);

        List<QuestionSetItemDto> questionSetItemDtos = questionSetService.getAllItemsByQuestionSet(questionSet)
                .stream()
                .map(QuestionSetItemDto::from)
                .toList();

        return QuestionSetDetailResponse.of(questionSet, questionSetItemDtos);
    }

    private void validateQuestionSetUser(User user, QuestionSet questionSet) {
        if (!user.getId().equals(questionSet.getUser().getId())) {
            throw new ApiException(QuestionSetErrorCode.QUESTION_SET_ACCESS_DENIED);
        }
    }
}
