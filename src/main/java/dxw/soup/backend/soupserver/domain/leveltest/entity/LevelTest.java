package dxw.soup.backend.soupserver.domain.leveltest.entity;

import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import dxw.soup.backend.soupserver.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "level_tests")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelTest extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_test_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @Column(name = "total_question_count")
    private Integer totalQuestionCount;

    @Column(name = "correct_count")
    private Integer correctCount;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column
    private Integer score;

    @Column
    @Enumerated(EnumType.STRING)
    private Soup resultSoup;

    public void updateTotalQuestionCount(Integer totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public void updateGradeResult(int correctCount, int score) {
        this.correctCount = correctCount;
        this.score = score;
    }

    public void finish(LocalDateTime now) {
        this.finishedAt = now;
    }
}
