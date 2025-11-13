package dxw.soup.backend.soupserver.domain.leveltest.facade;

import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestCreateRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestGradeRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestDetailResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestFindAllResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestQuestionDto;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.leveltest.event.LevelTestUpdateGradeResultEvent;
import dxw.soup.backend.soupserver.domain.leveltest.exception.LevelTestErrorCode;
import dxw.soup.backend.soupserver.domain.leveltest.repository.LevelTestQuestionRepository;
import dxw.soup.backend.soupserver.domain.leveltest.service.LevelTestQuestionSelector;
import dxw.soup.backend.soupserver.domain.leveltest.service.LevelTestService;
import dxw.soup.backend.soupserver.domain.model.dto.request.EvaluateLevelTestRequest;
import dxw.soup.backend.soupserver.domain.model.dto.request.GenerateLevelTestRequest;
import dxw.soup.backend.soupserver.domain.model.dto.response.EvaluateLevelTestResponse;
import dxw.soup.backend.soupserver.domain.model.dto.response.GenerateLevelTestResponse;
import dxw.soup.backend.soupserver.domain.model.exception.ModelErrorCode;
import dxw.soup.backend.soupserver.domain.model.external.ModelClient;
import dxw.soup.backend.soupserver.domain.question.dto.response.SubjectUnitDto;
import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.question.service.SubjectUnitService;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.service.UserQuestionService;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LevelTestFacade {
    private final LevelTestService levelTestService;
    private final UserService userService;
    private final SubjectUnitService subjectUnitService;
    private final LevelTestQuestionSelector levelTestQuestionSelector;
    private final ModelClient modelClient;
    private final QuestionService questionService;
    private final ApplicationEventPublisher eventPublisher;
    private final LevelTestQuestionRepository levelTestQuestionRepository;
    private final UserQuestionService userQuestionService;

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
        List<SubjectUnit> subjectUnits = subjectUnitService.findAllByIds(request.subjectUnitIds());
        List<Question> questions;

        //초기 테스트가 아닐 때는 기존 학습 데이터를 바탕으로 수준테스트 문제 선정
        if (!request.isInitialTest()) {
            GenerateLevelTestRequest generateRequest = GenerateLevelTestRequest.of(
                    user.getSoup().name(),
                    user.getWorkbooks(),
                    subjectUnits.stream()
                            .collect(Collectors.toMap(
                                    SubjectUnit::getId,
                                    SubjectUnit::getName
                            ))
            );

            GenerateLevelTestResponse modelResponse = modelClient.generateLevelTest(generateRequest)
                    .getBody();

            validateGenerateLevelTestResponse(modelResponse);

            questions = questionService.getAllByIds(modelResponse.mapQuestionIds());
        } else {
            questions = levelTestQuestionSelector.selectQuestionsBySubjectUnits(subjectUnits);
        }

        levelTestService.createLevelTestUnits(newLevelTest, subjectUnits);

        List<LevelTestQuestionDto> levelTestQuestions = levelTestService.createLevelTestQuestions(newLevelTest,
                        questions)
                .stream().map(LevelTestQuestionDto::from)
                .toList();

        List<SubjectUnitDto> subjectUnitDtos = subjectUnits
                .stream()
                .map(SubjectUnitDto::from)
                .toList();

        newLevelTest.updateTotalQuestionCount(questions.size());

        return LevelTestDetailResponse.of(newLevelTest, levelTestQuestions, subjectUnitDtos);
    }

    private void validateGenerateLevelTestResponse(GenerateLevelTestResponse modelResponse) {
        if (modelResponse == null) {
            throw new ApiException(ModelErrorCode.GENERATED_LEVEL_TEST_QUESTION_IS_EMPTY);
        }
    }

    public void gradeLevelTest(Long userId, Long levelTestId, LevelTestGradeRequest request) {
        User user = userService.findById(userId);
        LevelTest levelTest = levelTestService.findById(levelTestId);

        validateLevelTestUser(user, levelTest);

        List<LevelTestQuestion> levelTestQuestions = levelTestService.getAllLevelQuestionsByLevelTest(levelTest);

        // answers에서 questionId를 Key로, descriptiveImageUrl을 value로 하는 map 생성

        Map<String, String> questionIdToImageUrlMap = new HashMap<>();
        Map<String, String> questionIdToAnswerMap = new HashMap<>();

        request.answers().forEach(a -> {
            questionIdToImageUrlMap.put(a.questionId(), a.descriptiveImageUrl());// null OK
            questionIdToAnswerMap.put(a.questionId(), a.userAnswer());
        });

        List<Question> questions = questionService.getAllByIds(new ArrayList<>(questionIdToImageUrlMap.keySet()));

        // Question으로 LevelTestResultItem 생성
        List<EvaluateLevelTestRequest.LevelTestResultItem> levelTestResultItems = questions.stream()
                .map(question -> EvaluateLevelTestRequest.LevelTestResultItem.of(
                        questionIdToImageUrlMap.get(question.getId()),
                        question.getText(),
                        question.getAnswer(),
                        question.getAnswerText(),
                        questionIdToAnswerMap.get(question.getId()),
                        question.getTopic()
                ))
                .toList();

        EvaluateLevelTestRequest evaluateRequest = EvaluateLevelTestRequest.of(levelTestResultItems);

        // 비동기로 채점 요청
        evaluateLevelTestAsync(userId, levelTestId, evaluateRequest, levelTestQuestions);
    }

    @Async
    @Transactional
    public void evaluateLevelTestAsync(
            Long userId,
            Long levelTestId,
            EvaluateLevelTestRequest evaluateRequest,
            List<LevelTestQuestion> levelTestQuestions
    ) {
        CompletableFuture.supplyAsync(() -> {
            log.info("[Async-evaluateLevelTestAsync] 시작 levelTestId : {}", levelTestId);
            // modelClient를 통해 채점 요청
            return modelClient.evaluateEssayLevelTest(evaluateRequest)
                    .getBody();
        }).thenAccept(evaluateResponse -> {
            if (evaluateResponse != null && evaluateResponse.results() != null) {
                List<EvaluateLevelTestResponse.EvaluationResult> results = evaluateResponse.results();

                int correctCount = 0;
                int score = 0;

                for (int i = 0; i < Math.min(results.size(), levelTestQuestions.size()); i++) {
                    EvaluateLevelTestResponse.EvaluationResult result = results.get(i);
                    LevelTestQuestion levelTestQuestion = levelTestQuestions.get(i);

                    log.info("index: {}", i);
                    log.info("levelTestQuestion Number: {}", levelTestQuestion.getQuestionNumber());
                    log.info("result.userAnswer: {}", result.userAnswer());
                    log.info("result.score: {}", result.score());
                    log.info("result.essayTypeScoreText: {}", result.essayTypeScoreText());
                    log.info("result.isCorrect: {}", result.isCorrect());

                    int questionScore = ((result.score() != null) ? result.score() : 0);

                    if (result.isCorrect() != null && result.isCorrect()) {
                        correctCount++;
                        questionScore = ((result.score() != null) ? result.score() : 5);
                        score += questionScore;
                    }

                    levelTestQuestion.updateGradeResult(
                            result.isCorrect() != null ? result.isCorrect() : false,
                            result.userAnswer() != null ? result.userAnswer() : "",
                            result.score() != null ? result.score() : questionScore,
                            result.essayTypeScoreText()
                    );

                    levelTestQuestionRepository.save(levelTestQuestion);
                }

                levelTestService.updateGradeResult(levelTestId, correctCount, score);
                User user = userService.findById(userId);

                userQuestionService.createOrUpdateUserQuestionByLevelTestQuestions(user, levelTestQuestions);
                log.info("[Async-evaluateLevelTestAsync] 성공. levelTestId: {}, total: {}", levelTestId, results.size());
            }
        });
    }
}
