package dxw.soup.backend.soupserver.domain.planner.service;

import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlannerFlameScheduler {
    private final PlannerService plannerService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void updatePlannerFlameRunDate() {
        //매일 0시에 플래너 확인 후 flame이면 user의 lastFlameDate 어제 날짜로 업데이트하고,
        //아니라면 -> 사용자의 flameRunDateCount 0으로 초기화
        LocalDate yesterday = LocalDate.now().minusDays(1);

        log.info("플래너 불꽃 스케쥴러 시작 {}", yesterday);

        List<Planner> planners = plannerService.findAllByDate(yesterday);

        planners.forEach(planner -> {
            User user = planner.getUser();

            if (planner.isFlame()) {
                //flame이면 user의 lastFlameDate 어제 날짜로 업데이트하고,
                user.updateLastFlameDate(yesterday);
                user.addFlameRunDateCount();
            } else {
                user.resetFlameRunDateCount();
            }

            log.info("사용자 플래너 불꽃 상태 업데이트 완료 userNickname={}, plannerId={}", user.getNickname(), planner.getId());
        });

        log.info("플래너 불꽃 스케쥴러 종료");
    }
}
