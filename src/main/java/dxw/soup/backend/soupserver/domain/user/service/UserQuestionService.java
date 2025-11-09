package dxw.soup.backend.soupserver.domain.user.service;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
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

    @Transactional
    public void createOrUpdateUserQuestionTryCount(User user, List<Question> questions) {
        List<UserQuestion> userQuestions = getAllByUser(user);

        questions.forEach(question -> {
            UserQuestion userQuestion = userQuestions.stream()
                    .filter(uq -> uq.getQuestion().getId().equals(question.getId()))
                    .findFirst()
                    .orElseGet(() ->
                            UserQuestion.builder()
                                    .user(user)
                                    .question(question)
                                    .tryCount(0)
                                    .build()
                    );

            userQuestion.addTryCount();

            userQuestionRepository.save(userQuestion);
        });
    }

    public List<UserQuestion> getAllByUser(User user) {
        return userQuestionRepository.findALlByUser(user);
    }
}
