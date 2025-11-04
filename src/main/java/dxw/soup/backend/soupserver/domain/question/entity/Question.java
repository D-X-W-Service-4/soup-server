package dxw.soup.backend.soupserver.domain.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "questions")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @Column(name = "question_id", nullable = false)
    private String id;

    @Lob
    @Column(name = "file_name", nullable = false, columnDefinition = "TEXT")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_unit_id", nullable = false)
    private SubjectUnit subjectUnit;

    private Integer difficulty;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "question_type", nullable = false)
    private String questionType;

    @Column(name = "question_format", nullable = false)
    private String questionFormat;

    @Lob
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Lob
    @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Lob
    @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
    private String answerText;
}
