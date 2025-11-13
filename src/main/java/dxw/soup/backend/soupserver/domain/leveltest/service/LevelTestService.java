package dxw.soup.backend.soupserver.domain.leveltest.service;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestUnit;
import dxw.soup.backend.soupserver.domain.leveltest.exception.LevelTestErrorCode;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestQuestionRepository;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestRepository;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestUnitRepository;
import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import dxw.soup.backend.soupserver.domain.user.repository.UserRepository;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LevelTestService {
    private final LevelTestRepository levelTestRepository;
    private final LevelTestQuestionRepository levelTestQuestionRepository;
    private final LevelTestUnitRepository levelTestUnitRepository;

    public LevelTest findById(Long levelTestId) {
        return levelTestRepository.findById(levelTestId)
                .orElseThrow(() -> new ApiException(LevelTestErrorCode.LEVEL_TEST_NOT_FOUND));
    }

    public List<LevelTest> getAllLevelTestsByUser(User user) {
        return levelTestRepository.findAllByUser(user);
    }

    public List<LevelTestQuestion> getAllLevelQuestionsByLevelTest(LevelTest levelTest) {
        return levelTestQuestionRepository.findAllByLevelTest(levelTest);
    }

    public List<SubjectUnit> getAllSubjectUnitsByLevelTest(LevelTest levelTest) {
        return levelTestUnitRepository.findAllByLevelTest(levelTest)
                .stream().map(LevelTestUnit::getSubjectUnit)
                .toList();
    }

    public LevelTest createNewLevelTest(User user) {
        LevelTest levelTest = LevelTest.builder()
                .user(user)
                .build();

        return levelTestRepository.save(levelTest);
    }


    public List<LevelTestUnit> createLevelTestUnits(LevelTest levelTest, List<SubjectUnit> subjectUnits) {
        List<LevelTestUnit> levelTestUnits = subjectUnits.stream()
                .map(subjectUnit ->
                        LevelTestUnit.builder()
                                .levelTest(levelTest)
                                .subjectUnit(subjectUnit)
                                .build()
                )
                .toList();

        return levelTestUnitRepository.saveAll(levelTestUnits);
    }

    public List<LevelTestQuestion> createLevelTestQuestions(LevelTest levelTest, List<Question> questions) {
        Collections.shuffle(questions);

        AtomicInteger questionNumber = new AtomicInteger(1);

        List<LevelTestQuestion> levelTestQuestions = questions.stream()
                .map(question ->
                        LevelTestQuestion.builder()
                                .levelTest(levelTest)
                                .question(question)
                                .questionNumber(questionNumber.getAndIncrement())
                                .build()
                )
                .toList();

        return levelTestQuestionRepository.saveAll(levelTestQuestions);
    }

    public boolean existsByUser(User user) {
        return levelTestRepository.existsByUser(user);
    }

    @Transactional
    public void updateGradeResult(Long levelTestId, int correctCount, int score) {
        LevelTest levelTest = findById(levelTestId);

        levelTest.updateGradeResult(correctCount, score);
        levelTest.finish(LocalDateTime.now());
        levelTestRepository.save(levelTest);
    }
}
