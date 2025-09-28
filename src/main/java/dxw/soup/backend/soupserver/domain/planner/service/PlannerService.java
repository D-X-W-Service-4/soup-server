package dxw.soup.backend.soupserver.domain.planner.service;

import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerCreateRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerFlameItem;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerItemDto;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerResponse;
import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import dxw.soup.backend.soupserver.domain.planner.repository.PlannerItemRepository;
import dxw.soup.backend.soupserver.domain.planner.repository.PlannerRepository;
import dxw.soup.backend.soupserver.domain.planner.exception.PlannerErrorCode;
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
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final PlannerItemRepository plannerItemRepository;
    private final UserService userService;

    @Transactional
    public PlannerResponse createPlanner(Long userId, PlannerCreateRequest request) {
        User user = userService.findById(userId);

        Planner planner = Planner.builder()
                .user(user)
                .date(request.date())
                .flame(true)
                .build();

        Planner savedPlanner = plannerRepository.save(planner);

        List<PlannerItem> plannerItems = plannerItemRepository.findByPlannerId(savedPlanner.getId());
        List<PlannerItemDto> itemDto = plannerItems.stream()
                .map(PlannerItemDto::from)
                .toList();

        return PlannerResponse.from(savedPlanner, itemDto);
    }

    @Transactional
    public void deletePlanner(Long userId, Long plannerId) {
        Planner planner = findPlannerByIdAndUserId(userId, plannerId);
        plannerRepository.delete(planner);
    }

    @Transactional
    public void updateFeedback(Long userId, Long plannerId, PlannerFeedback feedback) {
        Planner planner = findPlannerByIdAndUserId(userId, plannerId);
        planner.updateFeedback(feedback);
    }

    @Transactional
    public void updatePlannerItemCheck(Long userId, Long plannerItemId, boolean checked) {
        PlannerItem plannerItem = plannerItemRepository.findById(plannerItemId)
                .orElseThrow(() -> new ApiException(PlannerErrorCode.PLANNER_ITEM_NOT_FOUND));

        if (!plannerItem.getPlanner().getUser().getId().equals(userId)) {
            throw new ApiException(PlannerErrorCode.PLANNER_ACCESS_DENIED);
        }

        plannerItem.updateChecked(checked);
    }

    private Planner findPlannerByIdAndUserId(Long userId, Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new ApiException(PlannerErrorCode.PLANNER_NOT_FOUND));

        if (!planner.getUser().getId().equals(userId)) {
            throw new ApiException(PlannerErrorCode.PLANNER_ACCESS_DENIED);
        }

        return planner;
    }

    public PlannerResponse getPlannerByDate(Long userId, LocalDate date) {
        Planner planner = plannerRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new ApiException(PlannerErrorCode.PLANNER_NOT_FOUND));

        List<PlannerItem> plannerItems = plannerItemRepository.findByPlannerId(planner.getId());
        List<PlannerItemDto> itemDto = plannerItems.stream()
                .map(PlannerItemDto::from)
                .toList();

        return PlannerResponse.from(planner, itemDto);
    }
}