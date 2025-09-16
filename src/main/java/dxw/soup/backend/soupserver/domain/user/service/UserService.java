package dxw.soup.backend.soupserver.domain.user.service;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.exception.UserErrorCode;
import dxw.soup.backend.soupserver.domain.user.repository.UserRepository;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(Long userId, Grade grade, Integer term, SubjectUnit lastSubjectUnit, Double studyHours, List<String> workbooks) {
        User user = findById(userId);
        String workbookText = String.join(",", workbooks);

        user.register(grade, term, lastSubjectUnit, studyHours, workbookText);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public void updateNickname(Long userId, String nickname) {
        User user = findById(userId);

        user.updateNickname(nickname);
    }
}
