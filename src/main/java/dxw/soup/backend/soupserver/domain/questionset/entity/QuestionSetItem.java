package dxw.soup.backend.soupserver.domain.questionset.entity;

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
@Table(name = "question_set_items")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionSetItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_set_item_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_set_id", nullable = false)
    private QuestionSet questionSet;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Builder.Default
    @Column(name = "is_correct", columnDefinition = "TINYINT(1)")
    private boolean isCorrect = false;

    @Column(name = "user_answer")
    private String userAnswer;

    @Column(name = "descriptive_image_url")
    private String descriptiveImageUrl;

    @Builder.Default
    @Column(name = "is_timeout", columnDefinition = "TINYINT(1)")
    private boolean isTimeout = false;

    @Column(name = "essay_type_score")
    private Integer essayTypeScore;

    @Lob
    @Column(name = "essay_type_score_text", columnDefinition = "TEXT")
    private String essayTypeScoreText;
}
