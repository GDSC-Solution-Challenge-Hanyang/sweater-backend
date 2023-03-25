//package gdsc.sc.sweater.common.exception;
//
//import gdsc.sc.sweater.entity.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/test")
//@RequiredArgsConstructor
//
//public class TestController {
//
//    private final TestRepository testRepository;
//
//
//    @GetMapping(value="/post", produces = "application/json; charset=utf8")
//    public ResponseEntity<MemberResponse> getMember(@RequestParam Long id) {
//        Optional<Member> member = testRepository.findById(id);
//        if (member.isEmpty())
//            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
//        MemberResponse memberResponse = new MemberResponse(member.get().getId(), member.get().getNickname());
//        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
//    }
//
//    public class MemberResponse {
//        public Long memberId;
//        public String name;
//
//        public MemberResponse(Long id, String nickname) {
//            this.memberId = id;
//            this.name = nickname;
//        }
//    }
//}
