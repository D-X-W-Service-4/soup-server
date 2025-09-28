package dxw.soup.backend.soupserver.domain.planner.repository;

import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Optional<Planner> findByUserIdAndDate(Long userId, LocalDate date);

    @Query("SELECT p FROM Planner p WHERE p.user.id = :userId AND p.date BETWEEN :startDate AND :endDate")
    List<Planner> findByUserIdAndDateBetween(@Param("userId") Long userId,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    boolean existsByUserIdAndDate(Long userId, LocalDate date);
}