package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.CreateMemberResponse;
import gdsc.sc.sweater.member.dto.FollowRequestListResponse;
import gdsc.sc.sweater.member.dto.MentorListResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<CreateMemberResponse> signup(@RequestBody CreateMemberRequest createMemberRequest) {
        Member member = memberService.create(createMemberRequest);
        System.out.println("Member: " + member); // Add this line to log the returned member

        return new ResponseEntity<>(new CreateMemberResponse(member.getId(), member.getEmail()),HttpStatus.OK);
    }


    /**
     * Mentor List [멘티] 멘토 리스트
     */
    @GetMapping("/mentor-list")
    public ResponseEntity<List<MentorListResponse>> getMentorList(@RequestParam Long memberId) {
        List<MentorListResponse> mentorList = memberService.getMentorList(memberId);
        return new ResponseEntity<>(mentorList, HttpStatus.OK);
    }


    /**
     * Mentor Application List [멘티] 멘토 신청 목록
     */
    @GetMapping("mentor-application-list")
    public ResponseEntity<List<FollowRequestListResponse>> getMentorApplicationList(@RequestParam Long memberId) {
        List<FollowRequestListResponse> mentorApplicationList = memberService.getMentorApplicationList(memberId);
        return new ResponseEntity<>(mentorApplicationList, HttpStatus.OK);
    }


    /**
     * Mentee Application List [멘토]멘티 신청 목록
     */
    @GetMapping("/mentee-application-list")
    public ResponseEntity<List<FollowRequestListResponse>> getMenteeApplicationList(@RequestParam Long memberId) {
        List<FollowRequestListResponse> mentorList = memberService.getMenteeApplicationList(memberId);
        return new ResponseEntity<>(mentorList, HttpStatus.OK);
    }


}
