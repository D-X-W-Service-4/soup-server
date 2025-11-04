package dxw.soup.backend.soupserver.domain.question.repository;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findAllBySubjectUnitInAndDifficulty(Collection<SubjectUnit> subjectUnits, Integer difficulty);
}
