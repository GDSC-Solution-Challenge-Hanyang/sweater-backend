package gdsc.sc.sweater.member.dto;

import gdsc.sc.sweater.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateMemberRequest {

    String nickName;
    String email;
    String pwd;
    MemberRole role;

}
