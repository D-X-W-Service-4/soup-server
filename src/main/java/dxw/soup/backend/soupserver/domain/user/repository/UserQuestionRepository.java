package dxw.soup.backend.soupserver.domain.user.repository;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    List<UserQuestion> findAllByUser(User user);

    @Query("""
        SELECT uq
        FROM UserQuestion uq
        JOIN uq.question q
        JOIN q.subjectUnit su
        WHERE uq.user.id = :userId
          AND (:grade IS NULL OR su.grade = :grade)
          AND (:subjectUnitId IS NULL OR su.id = :subjectUnitId)
          AND (:term IS NULL OR su.term = :term)
          AND (
                :filter IS NULL
             OR :filter = 'ALL'
             OR (:filter = 'STARRED' AND uq.isStarred = true)
             OR (:filter = 'INCORRECT' AND uq.answeredWrongBefore = true)
          )
        """)
    List<UserQuestion> findAllByFilter(
            @Param("userId") Long userId,
            @Param("filter") String filter,
            @Param("grade") Grade grade,
            @Param("term") Integer term,
            @Param("subjectUnitId") Long subjectUnitId
    );

    Optional<UserQuestion> findByUserAndQuestion(User user, Question question);

    List<UserQuestion> findAllByUserAndQuestionIn(User user, List<Question> questions);
}
