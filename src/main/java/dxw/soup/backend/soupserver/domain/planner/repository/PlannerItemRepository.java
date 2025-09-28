package dxw.soup.backend.soupserver.domain.planner.repository;

import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerItemRepository extends JpaRepository<PlannerItem, Long> {
    List<PlannerItem> findByPlannerId(Long plannerId);
}