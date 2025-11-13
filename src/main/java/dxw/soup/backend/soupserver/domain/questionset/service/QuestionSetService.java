package dxw.soup.backend.soupserver.domain.questionset.service;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSetItem;
import dxw.soup.backend.soupserver.domain.questionset.exception.QuestionSetErrorCode;
import dxw.soup.backend.soupserver.domain.questionset.repository.QuestionSetItemRepository;
import dxw.soup.backend.soupserver.domain.questionset.repository.QuestionSetRepository;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionSetService {
    private final QuestionSetRepository questionSetRepository;
    private final QuestionSetItemRepository questionSetItemRepository;

    public QuestionSet findById(Long questionSetId) {
        return questionSetRepository.findById(questionSetId)
                .orElseThrow(() -> new ApiException(QuestionSetErrorCode.QUESTION_SET_NOT_FOUND));
    }

    public QuestionSet createQuestionSet(User user, int totalQuestionCount) {
        QuestionSet questionSet = QuestionSet.builder()
                .user(user)
                .totalQuestionCount(totalQuestionCount)
                .build();

        return questionSetRepository.save(questionSet);
    }

    public List<QuestionSetItem> createQuestionSetItems(QuestionSet questionSet, List<Question> questions) {
        List<QuestionSetItem> questionSetItems = questions.stream()
                .map(question ->
                        QuestionSetItem.builder()
                                .questionSet(questionSet)
                                .question(question)
                                .build()
                )
                .toList();

        return questionSetItemRepository.saveAll(questionSetItems);
    }

    public List<QuestionSet> getAllQuestionSetsByUser(User user) {
        return questionSetRepository.findAllByUser(user);
    }

    public List<QuestionSetItem> getAllItemsByQuestionSet(QuestionSet questionSet) {
        return questionSetItemRepository.findAllByQuestionSet(questionSet);
    }

    public void updateGradeResult(Long questionSetId, int correctCount) {
        QuestionSet questionSet = findById(questionSetId);

        questionSet.updateGradeResult(correctCount);
    }
}
