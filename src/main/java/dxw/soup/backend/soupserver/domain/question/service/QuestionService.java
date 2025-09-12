package dxw.soup.backend.soupserver.domain.question.service;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.exception.QuestionErrorCode;
import dxw.soup.backend.soupserver.domain.question.repository.SubjectUnitRepository;
import dxw.soup.backend.soupserver.global.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final SubjectUnitRepository subjectUnitRepository;

    public SubjectUnit findSubjectUnitById(Long subjectUnitId) {
        return subjectUnitRepository.findById(subjectUnitId)
                .orElseThrow(() -> new ApiException(QuestionErrorCode.SUBJECT_UNIT_NOT_FOUND));
    }
}
