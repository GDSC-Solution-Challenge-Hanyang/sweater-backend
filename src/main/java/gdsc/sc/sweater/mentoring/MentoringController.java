package gdsc.sc.sweater.mentoring;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mentoring")
@AllArgsConstructor
public class MentoringController {

    private final MentoringService mentoringService;

    /**
     * Request Mentor (멘티)멘토링 신청
     */
    @PostMapping("/status")
    public ResponseEntity<String> applyMentoring(@RequestParam Long memberId, @RequestParam Long mentorId) {
        String result = mentoringService.applyMentoring(memberId, mentorId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Accept Mentoring request (멘토)멘토링 수락
     */
    @PatchMapping("/status")
    public ResponseEntity<String> acceptMentoringRequest(@RequestParam Long memberId, @RequestParam Long menteeId) {
        String result = mentoringService.acceptMentoringRequest(memberId, menteeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}