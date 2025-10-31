package dxw.soup.backend.soupserver.domain.leveltest.facade;

import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestDetailResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestFindAllResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestQuestionDto;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.leveltest.exception.LevelTestErrorCode;
import dxw.soup.backend.soupserver.domain.leveltest.service.LevelTestService;
import dxw.soup.backend.soupserver.domain.question.dto.response.SubjectUnitDto;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
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
public class LevelTestFacade {
    private final LevelTestService levelTestService;
    private final UserService userService;

    public LevelTestFindAllResponse getAllLevelTests(Long userId) {
        User user = userService.findById(userId);
        List<LevelTest> levelTests = levelTestService.getAllLevelTestsByUser(user);

        return LevelTestFindAllResponse.of(levelTests);
    }

    public LevelTestDetailResponse getLevelTestById(Long userId, Long levelTestId) {
        User user = userService.findById(userId);
        LevelTest levelTest = levelTestService.findById(levelTestId);

        validateLevelTestUser(user, levelTest);

        List<LevelTestQuestionDto> levelTestQuestions = levelTestService.getAllLevelQuestionsByLevelTest(
                levelTest)
                .stream()
                .map(LevelTestQuestionDto::from)
                .toList();

        List<SubjectUnitDto> subjectUnits = levelTestService.getAllSubjectUnitsByLevelTest(levelTest)
                .stream()
                .map(SubjectUnitDto::from)
                .toList();

        return LevelTestDetailResponse.of(levelTest, levelTestQuestions, subjectUnits);
    }

    private void validateLevelTestUser(User user, LevelTest levelTest) {
        if (!user.getId().equals(levelTest.getUser().getId())) {
            throw new ApiException(LevelTestErrorCode.LEVEL_TEST_ACCESS_DENIED);
        }
    }
}
