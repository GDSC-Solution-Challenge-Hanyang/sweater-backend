package gdsc.sc.sweater.member.dto;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.enums.MentoringStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FollowRequestListResponse {
    private Long memberId;
    private String nickname;
    private String description;
    private boolean isAccepted;

    public FollowRequestListResponse(Member member, Long menteeId) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.description = member.getDescription();
        if (member.getMenteeList()
                .stream()
                .anyMatch(mentoring -> (mentoring.getMentee().getId().equals(menteeId) && mentoring.getStatus().equals(MentoringStatus.MATCHED)))){
            this.isAccepted = true;
        } else this.isAccepted = false;
    }
}