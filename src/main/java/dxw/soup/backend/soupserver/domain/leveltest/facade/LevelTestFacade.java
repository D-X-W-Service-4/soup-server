package dxw.soup.backend.soupserver.domain.leveltest.facade;

import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestCreateRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestGradeRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestDetailResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestFindAllResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestQuestionDto;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.exception.LevelTestErrorCode;
import dxw.soup.backend.soupserver.domain.leveltest.service.LevelTestQuestionSelector;
import dxw.soup.backend.soupserver.domain.leveltest.service.LevelTestService;
import dxw.soup.backend.soupserver.domain.question.dto.response.SubjectUnitDto;
import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.service.SubjectUnitService;
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
    private final SubjectUnitService subjectUnitService;
    private final LevelTestQuestionSelector levelTestQuestionSelector;

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

    @Transactional
    public LevelTestDetailResponse createLevelTest(Long userId, LevelTestCreateRequest request) {
        User user = userService.findById(userId);

        LevelTest newLevelTest = levelTestService.createNewLevelTest(user);

        //초기 테스트가 아닐 때는 기존 학습 데이터를 바탕으로 수준테스트 문제 선정
        if (!request.isInitialTest()) {
            //TODO: AI 생성 API 호출
            return LevelTestDetailResponse.of(newLevelTest, null,null);
        }

        List<SubjectUnit> subjectUnits = subjectUnitService.findAllByIds(request.subjectUnitIds());
        levelTestService.createLevelTestUnits(newLevelTest, subjectUnits);

        List<Question> questions = levelTestQuestionSelector.selectQuestionsBySubjectUnits(subjectUnits);
        List<LevelTestQuestionDto> levelTestQuestions = levelTestService.createLevelTestQuestions(newLevelTest, questions)
                .stream().map(LevelTestQuestionDto::from)
                .toList();

        List<SubjectUnitDto> subjectUnitDtos = subjectUnits
                .stream()
                .map(SubjectUnitDto::from)
                .toList();

        newLevelTest.updateTotalQuestionCount(questions.size());

        return LevelTestDetailResponse.of(newLevelTest, levelTestQuestions, subjectUnitDtos);
    }

    public LevelTestDetailResponse gradeLevelTest(Long userId, Long levelTestId, LevelTestGradeRequest request) {
        User user = userService.findById(userId);
        LevelTest levelTest = levelTestService.findById(levelTestId);

        validateLevelTestUser(user, levelTest);

        //TODO: AI에게 전체 요청해서 호출

        return LevelTestDetailResponse.of(levelTest, null, null);
    }
}
