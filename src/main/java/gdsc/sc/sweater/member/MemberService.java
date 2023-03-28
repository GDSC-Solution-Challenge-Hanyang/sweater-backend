package gdsc.sc.sweater.member;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    @Transactional
    public Member create(CreateMemberRequest memberRequest) {
        if (memberRepository.findMemberByEmail(memberRequest.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.MEMBER_DUPLICATED);
        }
        Member member = Member.createMemberByRequest(memberRequest);

        return memberRepository.save(member);
    }


}
