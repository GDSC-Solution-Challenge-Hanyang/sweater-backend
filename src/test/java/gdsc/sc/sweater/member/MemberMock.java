package gdsc.sc.sweater.member;

import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.CreateMemberResponse;

public class MemberMock {

    static CreateMemberRequest memberRequest() {
        return CreateMemberRequest.builder()
                .nickName("nickName")
                .email("email")
                .pwd("pwd")
                .role(MemberRole.MENTEE)
                .build();
    }

    static CreateMemberResponse memberResponse() {
        return CreateMemberResponse.builder()
                .memberId(1L)
                .email("email")
                .build();
    }

}
