package dxw.soup.backend.soupserver.domain.user.entity;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2Provider;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider", nullable = false)
    private OAuth2Provider socialProvider;

    @Column(name = "social_id", nullable = false, unique = true)
    private String socialId;

    @Column(unique = true)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "last_subject_unit_id")
    private SubjectUnit lastSubjectUnit;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private Integer term;

    @Column(name = "study_hours")
    private Double studyHours;

    //콤마(,)로 구분
    private String workbooks;

    @Enumerated(EnumType.STRING)
    private Soup soup;

    public void register(
            Grade grade,
            Integer term,
            SubjectUnit lastSubjectUnit,
            Double studyHours,
            String workbooks
    ) {
        this.grade = grade;
        this.term = term;
        this.lastSubjectUnit = lastSubjectUnit;
        this.studyHours = studyHours;
        this.workbooks = workbooks;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
