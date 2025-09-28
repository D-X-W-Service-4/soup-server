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
}