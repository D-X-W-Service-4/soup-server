package dxw.soup.backend.soupserver.domain.user.entity;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_questions")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_question_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean answeredWrongBefore;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isStarred;

    @Column(name = "try_count", nullable = false)
    private Integer tryCount;

    public void addTryCount() {
        this.tryCount++;
    }

    public void updateAnsweredWrongBefore(boolean answeredWrongBefore) {
        this.answeredWrongBefore = answeredWrongBefore;
    }
}
