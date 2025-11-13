package dxw.soup.backend.soupserver.domain.user.facade;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.question.service.SubjectUnitService;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionFindAllResponse;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionFindAllResponse.UserQuestionDto;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionStarFindAllResponse;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.domain.user.service.UserQuestionService;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQuestionFacade {
    private final UserService userService;
    private final UserQuestionService userQuestionService;
    private final SubjectUnitService subjectUnitService;
    private final QuestionService questionService;

    public UserQuestionFindAllResponse getAllQuestions(Long userId, UserQuestionFilter filter, Grade grade,
                                                       Integer term, Long subjectUnitId) {
        User user = userService.findById(userId);
        SubjectUnit subjectUnit = (subjectUnitId != null) ? subjectUnitService.findById(subjectUnitId) : null;

        List<UserQuestionDto> userQuestions = userQuestionService.getAllByFilter(user, filter, grade, term, subjectUnit)
                .stream()
                .map(UserQuestionDto::from)
                .toList();

        return UserQuestionFindAllResponse.of(userQuestions);
    }

    @Transactional
    public void starQuestion(Long userId, String questionId) {
        User user = userService.findById(userId);
        Question question = questionService.findById(questionId);
        UserQuestion userQuestion = userQuestionService.findOrCreateByUserAndQuestion(user, question);

        userQuestion.updateStarred(!userQuestion.isStarred());
    }

    public UserQuestionStarFindAllResponse getQuestionStar(Long userId, String[] questionIds) {
        User user = userService.findById(userId);
        List<Question> questions = questionService.getAllByIds(Arrays.asList(questionIds));
        List<UserQuestion> userQuestions = userQuestionService.findAllByUserAndQuestionIds(user,
                questions);

        Map<String, UserQuestion> userQuestionMap = userQuestions.stream()
                .collect(Collectors.toMap(
                        uq -> uq.getQuestion().getId(),
                        uq -> uq
                ));

        Map<String, Boolean> questionToStarMap = questions.stream()
                .collect(
                        HashMap::new,
                        (map, q) -> {
                            UserQuestion uq = userQuestionMap.get(q.getId());
                            map.put(q.getId(), uq != null ? uq.isStarred() : null);
                        },
                        Map::putAll
                );

        List<UserQuestionStarFindAllResponse.UserQuestionStarDto> stars =
                Arrays.stream(questionIds)
                        .map(qId -> {
                            Boolean starred = questionToStarMap.get(qId);
                            return UserQuestionStarFindAllResponse.UserQuestionStarDto.from(
                                    qId,
                                    starred != null && starred   // null → false 처리
                            );
                        })
                        .toList();

        return UserQuestionStarFindAllResponse.of(stars);
    }
}
