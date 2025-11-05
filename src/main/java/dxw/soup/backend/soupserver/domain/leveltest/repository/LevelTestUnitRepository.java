package dxw.soup.backend.soupserver.domain.leveltest.repository;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestUnit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelTestUnitRepository extends JpaRepository<LevelTestUnit, Long> {
    List<LevelTestUnit> findAllByLevelTest(LevelTest levelTest);
}
