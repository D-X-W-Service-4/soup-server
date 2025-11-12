package dxw.soup.backend.soupserver.domain.leveltest.repository;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LevelTestRepository extends JpaRepository<LevelTest, Long> {
    @Query("SELECT lt FROM LevelTest lt WHERE lt.user = :user ORDER BY lt.createdAt DESC")
    List<LevelTest> findAllByUser(User user);

    boolean existsByUser(User user);
}
