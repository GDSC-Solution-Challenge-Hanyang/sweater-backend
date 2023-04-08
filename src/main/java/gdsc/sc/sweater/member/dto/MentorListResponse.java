package gdsc.sc.sweater.member.dto;

import gdsc.sc.sweater.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MentorListResponse {

    private Long memberId;
    private String nickname;
    private String description;
    private boolean isApplied;
    private boolean isAccepted;

    public MentorListResponse(Member member, Long menteeId) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.description = member.getDescription();
        if (member.getMenteeList() == null) { //check by null not .size or .isEmpty
            this.isApplied = false;
        } else { //can't invoke stream if getMenteeList is null
            this.isApplied = member.getMenteeList().stream()
                    .anyMatch(mentoring -> mentoring.getMentee().getId().equals(menteeId));
        }
//        System.out.println("menteeList: " + member.getMenteeList().get(0)); stream으로 확인. 안하면 에러
    }
}

