package dxw.soup.backend.soupserver.domain.user.repository;

import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
    //푼 문제 수 카운트
    @Query("SELECT COUNT(uq) FROM UserQuestion uq WHERE uq.user = :user AND uq.tryCount > 0")
    Long countSolvedQuestions(@Param("user") User user);

    //별표한 문제 수 카운트
    @Query("SELECT COUNT(uq) FROM UserQuestion uq WHERE uq.user = :user AND uq.isStarred = true")
    Long countStarredQuestions(@Param("user") User user);

    List<UserQuestion> findALlByUser(User user);
}
