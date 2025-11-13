package dxw.soup.backend.soupserver.domain.leveltest.event.listener;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.leveltest.event.LevelTestUpdateGradeResultEvent;
import dxw.soup.backend.soupserver.domain.leveltest.service.LevelTestService;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.service.UserQuestionService;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class LevelTestUpdateGradeResultEventListener {
    private final UserQuestionService userQuestionService;
    private final UserService userService;
    private final LevelTestService levelTestService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUpdateGradeResult(LevelTestUpdateGradeResultEvent event) {
        log.info("LevelTestUpdateGradeResultEventListener received event: {}", event);
        User user = userService.findById(event.userId());
        LevelTest levelTest = levelTestService.findById(event.levelTestId());
        List<LevelTestQuestion> levelTestQuestions = levelTestService.getAllLevelQuestionsByLevelTest(levelTest);

        userQuestionService.createOrUpdateUserQuestionByLevelTestQuestions(user, levelTestQuestions);
        log.info("LevelTestUpdateGradeResultEventListener finished event: {}", event);
    }
}
