package gdsc.sc.sweater.member;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static gdsc.sc.sweater.member.MemberMock.memberRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @MockBean
    MemberRepository memberRepository;

    @Test
    void memberServiceIsNotNull() {
        assertThat(memberService).isNotNull();
    }

    @Test
    @DisplayName("맴버 생성 service")
    public void createMember_success() {
        // given
        CreateMemberRequest request = memberRequest();
        Member expectedMember = Member.createMemberByRequest(request);

        //when
        when(memberRepository.findMemberByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(expectedMember);
        Member actualMember = memberService.create(request);

        // then
        assertEquals("맴버 비교",expectedMember, actualMember);
        verify(memberRepository).findMemberByEmail(request.getEmail());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("맴버 생성 service 중복 예외")
    void createDuplicated() {
        //given
        CreateMemberRequest memberRequest = memberRequest();
        Member existingMember = Member.createMemberByRequest(memberRequest);
        //when
        when(memberRepository.findMemberByEmail(memberRequest.getEmail())).thenReturn(Optional.of(existingMember));
        //then
        CustomException exception = assertThrows(CustomException.class, () -> memberService.create(memberRequest));
        assertEquals("맴버 생성 중복로직", ErrorCode.MEMBER_DUPLICATED, exception.getErrorCode());
        verify(memberRepository).findMemberByEmail(memberRequest.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }
}
