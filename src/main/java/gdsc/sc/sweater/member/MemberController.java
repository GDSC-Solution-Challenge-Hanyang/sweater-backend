package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.CreateMemberResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
//@RequiredArgsConstructor
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<CreateMemberResponse> signup(@RequestBody CreateMemberRequest createMemberRequest) {
        Member member = memberService.create(createMemberRequest);
        System.out.println("Member: " + member); // Add this line to log the returned member

        return new ResponseEntity<>(new CreateMemberResponse(member.getId(), member.getEmail()),HttpStatus.OK);
    }
}
