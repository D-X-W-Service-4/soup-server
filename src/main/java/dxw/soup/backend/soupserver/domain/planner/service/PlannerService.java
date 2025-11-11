package dxw.soup.backend.soupserver.domain.planner.service;

import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerCreateRequest;
import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import dxw.soup.backend.soupserver.domain.planner.repository.PlannerItemRepository;
import dxw.soup.backend.soupserver.domain.planner.repository.PlannerRepository;
import dxw.soup.backend.soupserver.domain.planner.exception.PlannerErrorCode;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final PlannerItemRepository plannerItemRepository;

    public Planner findById(Long plannerId) {
        return plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(PlannerErrorCode.PLANNER_NOT_FOUND));
    }

    public Planner findByUserIdAndDate(Long userId, LocalDate date) {
        return plannerRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new ApiException(PlannerErrorCode.PLANNER_NOT_FOUND));
    }

    public List<Planner> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate) {
        return plannerRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    public List<PlannerItem> findItemsByPlannerId(Long plannerId) {
        return plannerItemRepository.findByPlannerId(plannerId);
    }

    public PlannerItem findItemById(Long plannerItemId) {
        return plannerItemRepository.findById(plannerItemId)
                .orElseThrow(() -> new ApiException(PlannerErrorCode.PLANNER_ITEM_NOT_FOUND));
    }

    @Transactional
    public Planner createPlanner(User user, LocalDate date) {
         if (plannerRepository.existsByUserIdAndDate(user.getId(), date)) {
             throw new ApiException(PlannerErrorCode.DUPLICATE_PLANNER);
         }

        Planner planner = Planner.builder()
                .user(user)
                .date(date)
                .flame(true)
                .build();

        return plannerRepository.save(planner);
    }

    @Transactional
    public List<PlannerItem> createPlannerItems(List<PlannerItem> plannerItems) {
        if (plannerItems.isEmpty()) {
            return List.of();
        }
        return plannerItemRepository.saveAll(plannerItems);
    }

    @Transactional
    public void delete(Planner planner) {
        plannerRepository.delete(planner);
    }

    @Transactional
    public void updateFeedback(Planner planner, PlannerFeedback feedback) {
        planner.updateFeedback(feedback);
    }

    @Transactional
    public void updateItemCheck(PlannerItem plannerItem, boolean checked) {
        plannerItem.updateChecked(checked);
    }

    @Transactional
    public void updateFlame(Planner planner, boolean flame) {
        planner.updateFlame(flame);
    }

    public List<Planner> findAllByDate(LocalDate date) {
        return plannerRepository.findAllByDate(date);
    }
}