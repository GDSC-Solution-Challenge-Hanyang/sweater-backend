package gdsc.sc.sweater.common.exception;

import gdsc.sc.sweater.Member;
import jakarta.persistence.TemporalType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor

public class TestController {

    private final TestRepository testRepository;
    @GetMapping("/post")
    public ResponseEntity<MemberResponse> getMember(@RequestParam Long id) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        try {
            Optional<Member> member = testRepository.findById(id);
            MemberResponse memberResponse = new MemberResponse(member.get().getId(), member.get().getNickname());
            return new ResponseEntity<>(memberResponse, resHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MemberNotExistException();
        }
    }

    public class MemberResponse {
        public Long memberId;
        public String name;

        public MemberResponse(Long id, String nickname) {
            this.memberId = id;
            this.name = nickname;
        }
    }

}
