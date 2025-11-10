package dxw.soup.backend.soupserver.domain.questionset.repository;

import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSetItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionSetItemRepository extends JpaRepository<QuestionSetItem, Long> {
    List<QuestionSetItem> findAllByQuestionSet(QuestionSet questionSet);
}
