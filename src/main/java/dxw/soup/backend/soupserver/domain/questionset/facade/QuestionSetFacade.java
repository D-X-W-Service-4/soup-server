package dxw.soup.backend.soupserver.domain.questionset.facade;

import dxw.soup.backend.soupserver.domain.model.dto.request.EvaluateLevelTestRequest;
import dxw.soup.backend.soupserver.domain.model.dto.response.EvaluateLevelTestResponse;
import dxw.soup.backend.soupserver.domain.model.external.ModelClient;
import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetCreateRequest;
import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetGradeRequest;
import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetGradeRequest.QuestionUserAnswer;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetDetailResponse;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetFindAllResponse;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetFindAllResponse.QuestionSetDto;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetItemDto;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSetItem;
import dxw.soup.backend.soupserver.domain.questionset.event.QuestionSetUpdateGradeResultEvent;
import dxw.soup.backend.soupserver.domain.questionset.exception.QuestionSetErrorCode;
import dxw.soup.backend.soupserver.domain.questionset.repository.QuestionSetItemRepository;
import dxw.soup.backend.soupserver.domain.questionset.service.QuestionSetService;
import dxw.soup.backend.soupserver.domain.user.entity.User;
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
public class QuestionSetFacade {
    private final QuestionService questionService;
    private final QuestionSetService questionSetService;
    private final UserService userService;
    private final ModelClient modelClient;
    private final ApplicationEventPublisher eventPublisher;
    private final QuestionSetItemRepository questionSetItemRepository;

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

    public List<QuestionSetItem> getAllItemsByQuestionSet(QuestionSet questionSet) {
        return questionSetItemRepository.findAllByQuestionSet(questionSet);
    }

    public void gradeQuestionSet(Long userId, Long questionSetId, QuestionSetGradeRequest request) {
        User user = userService.findById(userId);
        QuestionSet questionSet = questionSetService.findById(questionSetId);

        validateQuestionSetUser(user, questionSet);

        List<QuestionSetItem> questionSetItems = getAllItemsByQuestionSet(questionSet);

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
                        question.getTopic(),
                        question.getQuestionFormat()
                ))
                .toList();

        EvaluateLevelTestRequest evaluateRequest = EvaluateLevelTestRequest.of(levelTestResultItems);

        // 비동기로 채점 요청
        evaluateLevelTestAsync(userId, questionSetId, evaluateRequest, questionSetItems);
    }

    @Async
    @Transactional
    public void evaluateLevelTestAsync(
            Long userId,
            Long questionSetId,
            EvaluateLevelTestRequest evaluateRequest,
            List<QuestionSetItem> questionSetItems
    ) {
        CompletableFuture.supplyAsync(() -> {
            log.info("[Async-evaluateLevelTestAsync] 시작 questionSetId: {}", questionSetId);
            // modelClient를 통해 채점 요청
            return modelClient.evaluateEssayLevelTest(evaluateRequest)
                    .getBody();
        }).thenAccept(evaluateResponse -> {
            if (evaluateResponse != null && evaluateResponse.results() != null) {
                List<EvaluateLevelTestResponse.EvaluationResult> results = evaluateResponse.results();

                for (int i = 0; i < Math.min(results.size(), questionSetItems.size()); i++) {
                    EvaluateLevelTestResponse.EvaluationResult result = results.get(i);
                    QuestionSetItem questionSetItem = questionSetItems.get(i);

                    questionSetItem.updateGradeResult(
                            result.isCorrect() != null ? result.isCorrect() : false,
                            result.userAnswer() != null ? result.userAnswer() : "",
                            result.score(),
                            result.essayTypeScoreText()
                    );
                }

                eventPublisher.publishEvent(
                        new QuestionSetUpdateGradeResultEvent(
                                userId,
                                questionSetId
                        )
                );
                log.info("[Async-evaluateLevelTestAsync] 성공. questionSetId: {}, total: {}", questionSetId, results.size());
            }
        });
    }
}
