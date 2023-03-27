package gdsc.sc.sweater.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class CreateMemberResponse {
    Long memberId;
    String email;
}
