package dxw.soup.backend.soupserver.domain.leveltest.entity;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import dxw.soup.backend.soupserver.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "level_test_questions")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelTestQuestion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_test_question_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_test_id", nullable = false)
    private LevelTest levelTest;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "is_correct", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isCorrect;

    @Column(name = "user_answer", nullable = false)
    private String userAnswer;

    @Column(name = "descriptive_image_url")
    private String descriptiveImageUrl;

    @Column(name = "is_timeout", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isTimeout;

    @Column(name = "essay_type_score")
    private Integer essayTypeScore;

    @Lob
    @Column(name = "essay_type_score_text", columnDefinition = "TEXT")
    private String essayTypeScoreText;

    public void updateGradeResult(
            boolean isCorrect,
            String userAnswer,
            Integer essayTypeScore,
            String essayTypeScoreText
    ) {
        this.isCorrect = isCorrect;
        this.userAnswer = userAnswer;
        this.essayTypeScore = essayTypeScore;
        this.essayTypeScoreText = essayTypeScoreText;
    }
}
