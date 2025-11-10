package dxw.soup.backend.soupserver.domain.questionset.repository;

import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionSetRepository extends JpaRepository<QuestionSet, Long> {
    List<QuestionSet> findAllByUser(User user);
}
