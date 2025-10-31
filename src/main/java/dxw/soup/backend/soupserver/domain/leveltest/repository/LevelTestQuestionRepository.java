package dxw.soup.backend.soupserver.domain.leveltest.repository;


import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LevelTestQuestionRepository extends JpaRepository<LevelTestQuestion, Long> {
    @Query("SELECT ltq FROM LevelTestQuestion ltq WHERE ltq.levelTest = :levelTest ORDER BY ltq.questionNumber ASC")
    List<LevelTestQuestion> findAllByLevelTest(LevelTest levelTest);
}
