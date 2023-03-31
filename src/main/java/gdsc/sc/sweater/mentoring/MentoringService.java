package gdsc.sc.sweater.mentoring;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.enums.MentoringStatus;
import gdsc.sc.sweater.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MentoringService {

    private final MentoringRepository mentoringRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public String applyMentoring(Long memberId, Long mentorId) {
        Member mentee = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Member mentor = memberRepository.findById(mentorId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Mentoring mentoring = Mentoring.applyMentoring(mentee, mentor);
        mentoringRepository.save(mentoring);
        return "success";
    }

    /**
     * [멘토] 멘티 수락
     */
    @Transactional
    public String acceptMentoringRequest(Long memberId, Long menteeId) {
        Mentoring acceptedMentoring = mentoringRepository.findMentoringByMenteeIdAndMentorIdAndStatus(menteeId, memberId, MentoringStatus.PENDING);
        if (acceptedMentoring == null) {
            return "null";
        }
        acceptedMentoring.modifyStatusAsMatched();
        return "success";
    }
}