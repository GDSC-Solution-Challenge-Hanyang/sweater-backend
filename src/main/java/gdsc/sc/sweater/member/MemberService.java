package gdsc.sc.sweater.member;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.MentoringStatus;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.FollowRequestListResponse;
import gdsc.sc.sweater.member.dto.MentorListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member create(CreateMemberRequest memberRequest) {
        if (memberRepository.findMemberByEmail(memberRequest.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.MEMBER_DUPLICATED);
        }
        Member member = Member.createMemberByRequest(memberRequest);

        return memberRepository.save(member);
    }
//
//    /**
//     * [멘티] 멘토 리스트
//     */
//    public List<MentorListResponse> getMentorList(Long memberId) {
//        List<Member> mentorList = memberRepository.findAllByRoleAndStatus(MemberRole.MENTOR, Status.ACTIVE);
////        System.out.println("mentorList = " + mentorList.get(0).getNickname());
////        System.out.println("mentorList.get(0).getMenteeList().size() = " + mentorList.get(0).getMenteeList().size());
//        return mentorList.stream()
//                .map(m -> new MentorListResponse(m, memberId))
//                .collect(Collectors.toList());
//    }


//    /**
//     * [멘티] 멘토 신청 리스트
//     */
//    public List<FollowRequestListResponse> getMentorApplicationList(Long memberId) {
//        Member mentee = memberRepository.findById(memberId)
//                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//        List<Mentoring> mentorList = mentee.getMentorList();
//        return mentorList.stream() //distinct
//                .collect(Collectors.toMap(
//                        mentoring -> mentoring.getMentor().getId(),
//                        mentoring -> mentoring,
//                        (mentoring1, mentoring2) -> mentoring1
//                ))
//                .values()
//                .stream()
//                .map(mentoring -> new FollowRequestListResponse(mentoring.getMentor(), memberId))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * [멘토] 멘티 신청 리스트
//     */
//    public List<FollowRequestListResponse> getMenteeApplicationList(Long memberId) {
//        Member mentor = memberRepository.findById(memberId)
//                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//        List<Mentoring> menteeList = mentor.getMenteeList().stream().filter(mentoring -> mentoring.getStatus().equals(MentoringStatus.PENDING)).collect(Collectors.toList());
//        return menteeList.stream() //distinct
//                .collect(Collectors.toMap(
//                        mentoring -> mentoring.getMentee().getId(),
//                        mentoring -> mentoring,
//                        (mentoring1, mentoring2) -> mentoring1
//                ))
//                .values()
//                .stream()
//                .map(mentoring -> new FollowRequestListResponse(mentoring.getMentee(), memberId))
//                .collect(Collectors.toList());
//    }


    /**
     * [멘티] 멘토 리스트
     */
    public List<MentorListResponse> getMentorList(Long memberId) {
        List<Member> mentorList = memberRepository.findAllByRoleAndStatus(MemberRole.MENTOR, Status.ACTIVE);
//        System.out.println("mentorList = " + mentorList.get(0).getNickname());
//        System.out.println("mentorList.get(0).getMenteeList().size() = " + mentorList.get(0).getMenteeList().size());
        return mentorList.stream()
                .map(m -> new MentorListResponse(m, memberId))
                .collect(Collectors.toList());
    }


    /**
     * [멘티] 멘토 신청 리스트
     */
    public List<FollowRequestListResponse> getMentorApplicationList(Long memberId) {
        Member mentee = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Mentoring> mentorList = mentee.getMentorList();
        return mentorList.stream() //distinct
                .collect(Collectors.toMap(
                        mentoring -> mentoring.getMentor().getId(),
                        mentoring -> mentoring,
                        (mentoring1, mentoring2) -> mentoring1
                ))
                .values()
                .stream()
                .map(mentoring -> new FollowRequestListResponse(mentoring.getMentor(), memberId))
                .collect(Collectors.toList());
    }

    /**
     * [멘토] 멘티 신청 리스트
     */
    public List<FollowRequestListResponse> getMenteeApplicationList(Long memberId) {
        Member mentor = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Mentoring> menteeList = mentor.getMenteeList();
        return menteeList.stream() //distinct
                .collect(Collectors.toMap(
                        mentoring -> mentoring.getMentee().getId(),
                        mentoring -> mentoring,
                        (mentoring1, mentoring2) -> mentoring1
                ))
                .values()
                .stream()
                .map(mentoring -> new FollowRequestListResponse(mentoring.getMentee(), memberId))
                .collect(Collectors.toList());
    }


}
