package dxw.soup.backend.soupserver.domain.question.service;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.enums.Difficulty;
import dxw.soup.backend.soupserver.domain.question.exception.QuestionErrorCode;
import dxw.soup.backend.soupserver.domain.question.repository.QuestionRepository;
import dxw.soup.backend.soupserver.domain.question.repository.SubjectUnitRepository;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final SubjectUnitRepository subjectUnitRepository;
    private final QuestionRepository questionRepository;

    public Question findById(String questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiException(QuestionErrorCode.QUESTION_NOT_FOUND));
    }

    public SubjectUnit findSubjectUnitById(Long subjectUnitId) {
        return subjectUnitRepository.findById(subjectUnitId)
                .orElseThrow(() -> new ApiException(QuestionErrorCode.SUBJECT_UNIT_NOT_FOUND));
    }

    public List<Question> getQuestionsBySubjectUnitsAndDifficulty(List<SubjectUnit> subjectUnits, Difficulty difficulty) {
        return questionRepository.findAllBySubjectUnitInAndDifficulty(subjectUnits, difficulty.getMetadata());
    }

    public List<Question> getAllByIds(List<String> questionIds) {
        return questionRepository.findAllById(questionIds);
    }
}
