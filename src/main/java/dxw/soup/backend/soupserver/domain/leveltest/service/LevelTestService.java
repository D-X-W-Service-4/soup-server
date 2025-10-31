package dxw.soup.backend.soupserver.domain.leveltest.service;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestUnit;
import dxw.soup.backend.soupserver.domain.leveltest.exception.LevelTestErrorCode;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestQuestionRepository;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestRepository;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestUnitRepository;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
