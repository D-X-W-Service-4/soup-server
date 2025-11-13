package dxw.soup.backend.soupserver.domain.user.service;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.domain.user.repository.UserQuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQuestionService {
    private final UserQuestionRepository userQuestionRepository;

    public UserQuestion createUserQuestion(User user, Question question) {
        return userQuestionRepository.save(UserQuestion.builder()
                .user(user)
                .question(question)
                .answeredWrongBefore(false)
                .tryCount(0)
                .build());
    }

    public List<UserQuestion> findAllByUserAndQuestionIds(User user, List<Question> questions) {
        return userQuestionRepository.findAllByUserAndQuestionIn(user, questions);
    }

    public UserQuestion findOrCreateByUserAndQuestion(User user, Question question) {
        return userQuestionRepository.findByUserAndQuestion(user, question)
                .orElseGet(() -> createUserQuestion(user, question));
    }

    @Transactional
    public void createOrUpdateUserQuestionByLevelTestQuestions(User user, List<LevelTestQuestion> levelTestQuestions) {
        List<UserQuestion> userQuestions = getAllByUser(user);

        levelTestQuestions.forEach(ltq -> {
            Question question = ltq.getQuestion();
            UserQuestion userQuestion = userQuestions.stream()
                    .filter(uq -> uq.getQuestion().getId().equals(question.getId()))
                    .findFirst()
                    .orElseGet(() ->
                            UserQuestion.builder()
                                    .user(user)
                                    .question(question)
                                    .answeredWrongBefore(false)
                                    .tryCount(0)
                                    .build()
                    );

            if (!ltq.isCorrect()) {
                userQuestion.updateAnsweredWrongBefore(true);
            }

            userQuestion.addTryCount();

            userQuestionRepository.save(userQuestion);
        });
    }

    public List<UserQuestion> getAllByUser(User user) {
        return userQuestionRepository.findAllByUser(user);
    }

    public List<UserQuestion> getAllByFilter(
            User user,
            UserQuestionFilter filter,
            Grade grade,
            Integer term,
            SubjectUnit subjectUnit
    ) {
        Long subjectUnitId = (subjectUnit != null) ? subjectUnit.getId() : null;
        return userQuestionRepository.findAllByFilter(user.getId(), (filter != null) ? filter.name() : null, grade, term, subjectUnitId);
    }
}
