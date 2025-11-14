package dxw.soup.backend.soupserver.domain.leveltest.service;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.enums.Difficulty;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelTestQuestionSelector {
    private final QuestionService questionService;

    private static final int[] QUESTION_COUNT_BY_DIFFICULTY = {1, 2, 1};

    public List<Question> selectQuestionsBySubjectUnits(List<SubjectUnit> subjectUnits) {
        List<Question> results = new ArrayList<>();

        for (Difficulty difficulty : Difficulty.values()) {
            List<Question> questions = questionService.getQuestionsBySubjectUnitsAndDifficulty(
                    subjectUnits, difficulty);
            int questionCount = QUESTION_COUNT_BY_DIFFICULTY[difficulty.ordinal()];

            Collections.shuffle(questions);
            results.addAll(questions.subList(0, Math.min(questionCount, questions.size())));
        }

        return results;
    }
}