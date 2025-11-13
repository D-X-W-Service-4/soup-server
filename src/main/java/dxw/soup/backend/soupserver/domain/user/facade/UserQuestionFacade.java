package dxw.soup.backend.soupserver.domain.user.facade;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.service.SubjectUnitService;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionFindAllResponse;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionFindAllResponse.UserQuestionDto;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.domain.user.service.UserQuestionService;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import java.util.List;
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

    public UserQuestionFindAllResponse getAllQuestions(Long userId, UserQuestionFilter filter, Grade grade, Integer term, Long subjectUnitId) {
        User user = userService.findById(userId);
        SubjectUnit subjectUnit = (subjectUnitId != null) ? subjectUnitService.findById(subjectUnitId) : null;

        List<UserQuestionDto> userQuestions = userQuestionService.getAllByFilter(user, filter, grade, term, subjectUnit)
                .stream()
                .map(UserQuestionDto::from)
                .toList();

        return UserQuestionFindAllResponse.of(userQuestions);
    }
}
