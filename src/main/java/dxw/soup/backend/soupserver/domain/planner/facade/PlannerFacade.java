package dxw.soup.backend.soupserver.domain.planner.facade;

import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerCreateRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerFlameItem;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerFlameResponse;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerItemDto;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerResponse;
import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import dxw.soup.backend.soupserver.domain.planner.exception.PlannerErrorCode;
import dxw.soup.backend.soupserver.domain.planner.service.PlannerService;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlannerFacade {
    private final PlannerService plannerService;
    private final UserService userService;

    @Transactional
    public PlannerResponse createPlanner(Long userId, PlannerCreateRequest request) {
        User user = userService.findById(userId);

        //TODO: AI 생성 API 호출
        // AIPlanResponse aiResponse = modelClient.generatePlan(user.getId(), request.date());

        Planner planner = plannerService.createPlanner(user, request.date());

        //TODO: AI 응답으로 플래너 아이템 생성
        // List<PlannerItem> plannerItems = aiResponse.toPlannerItems(planner);
        // plannerService.createPlannerItems(planner, plannerItems);

        List<PlannerItem> plannerItems = plannerService.findItemsByPlannerId(planner.getId());
        List<PlannerItemDto> itemDtos = plannerItems.stream()
                .map(PlannerItemDto::from)
                .toList();

        return PlannerResponse.from(planner, itemDtos);
    }

    @Transactional
    public void deletePlanner(Long userId, Long plannerId) {
        User user = userService.findById(userId);
        Planner planner = plannerService.findById(plannerId);

        validatePlannerOwner(user, planner);

        plannerService.delete(planner);
    }

    @Transactional
    public void updatePlannerFeedback(Long userId, Long plannerId, PlannerFeedback feedback) {
        User user = userService.findById(userId);
        Planner planner = plannerService.findById(plannerId);

        validatePlannerOwner(user, planner);

        plannerService.updateFeedback(planner, feedback);
    }

    @Transactional
    public void updatePlannerItemCheck(Long userId, Long plannerItemId, boolean checked) {
        User user = userService.findById(userId);
        PlannerItem plannerItem = plannerService.findItemById(plannerItemId);

        validatePlannerItemOwner(user, plannerItem);

        plannerService.updateItemCheck(plannerItem, checked);

        Planner planner = plannerItem.getPlanner();
        List<PlannerItem> allItems = plannerService.findItemsByPlannerId(planner.getId());
        boolean allChecked = allItems.stream().allMatch(PlannerItem::isChecked);

        plannerService.updateFlame(planner, allChecked);
    }

    public PlannerResponse getPlannerByDate(Long userId, LocalDate date) {
        User user = userService.findById(userId);
        Planner planner = plannerService.findByUserIdAndDate(user.getId(), date);

        List<PlannerItem> plannerItems = plannerService.findItemsByPlannerId(planner.getId());
        List<PlannerItemDto> itemDtos = plannerItems.stream()
                .map(PlannerItemDto::from)
                .toList();

        return PlannerResponse.from(planner, itemDtos);
    }

    public PlannerFlameResponse getPlannerFlames(Long userId, LocalDate startDate, LocalDate endDate) {
        User user = userService.findById(userId);
        List<Planner> planners = plannerService.findByUserIdAndDateBetween(user.getId(), startDate, endDate);

        List<PlannerFlameItem> flames = planners.stream()
                .map(planner -> PlannerFlameItem.of(planner.getDate(), planner.isFlame()))
                .toList();

        return PlannerFlameResponse.of(flames);
    }

    private void validatePlannerOwner(User user, Planner planner) {
        if (!planner.getUser().getId().equals(user.getId())) {
            throw new ApiException(PlannerErrorCode.PLANNER_ACCESS_DENIED);
        }
    }

    private void validatePlannerItemOwner(User user, PlannerItem plannerItem) {
        if (!plannerItem.getPlanner().getUser().getId().equals(user.getId())) {
            throw new ApiException(PlannerErrorCode.PLANNER_ACCESS_DENIED);
        }
    }
}