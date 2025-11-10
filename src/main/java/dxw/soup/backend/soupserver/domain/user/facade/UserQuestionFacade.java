package dxw.soup.backend.soupserver.domain.user.facade;

import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQuestionFacade {
    private final UserService userService;

    public QuestionDto getAllQuestions(Long userId, UserQuestionFilter filter, String semester, Long subjectUnitId) {
        User user = userService.findById(userId);



        return null;
    }
}
