package dxw.soup.backend.soupserver.domain.planner.entity;

import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planners")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planner extends BaseTimeEntity {
    @Id
    @Column(name = "planner_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean flame;

    @Enumerated(EnumType.STRING)
    private PlannerFeedback feedback;

    public void updateFeedback(PlannerFeedback feedback) {
        this.feedback = feedback;
    }

    public void updateFlame(boolean flame) {
        this.flame = flame;
    }
}
