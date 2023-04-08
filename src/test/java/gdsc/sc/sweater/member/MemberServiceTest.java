package gdsc.sc.sweater.member;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.MentoringStatus;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.FollowRequestListResponse;
import gdsc.sc.sweater.member.dto.MentorListResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static gdsc.sc.sweater.member.MemberMock.memberRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals("맴버 비교", expectedMember, actualMember);
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


    @Test
    public void getMentorListTest() {
        Long memberId = 1L;
        List<Member> mentorList = Arrays.asList(
                Member.createTestMemberWithId(10L, "mentor1", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 1", null, null),
                Member.createTestMemberWithId(11L, "mentor2", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 2", null, null)
        );

        when(memberRepository.findAllByRoleAndStatus(MemberRole.MENTOR, Status.ACTIVE)).thenReturn(mentorList);

        List<MentorListResponse> result = memberService.getMentorList(memberId);

        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getMemberId());
        assertEquals("mentor1", result.get(0).getNickname());
        assertEquals("Description 1", result.get(0).getDescription());

        assertEquals(11L, result.get(1).getMemberId());
        assertEquals("mentor2", result.get(1).getNickname());
        assertEquals("Description 2", result.get(1).getDescription());

    }


    @Test
    public void getMentorApplicationListTest() {
        Long memberId = 1L;

        Member mentor1 = Member.createTestMemberWithId(10L, "mentor1", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 1", null, null);
        Member mentor2 = Member.createTestMemberWithId(11L, "mentor2", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 2", null, null);
        Member mentee = Member.createTestMemberWithId(1L, "mentee", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTEE, "Description 2", null, null);

        Mentoring mentoring1 = Mentoring.createMentoringForTest(1L, mentee, mentor1, MentoringStatus.PENDING);
        Mentoring mentoring2 = Mentoring.createMentoringForTest(2L, mentee, mentor2, MentoringStatus.PENDING);
        mentee.setMentorList(Arrays.asList(mentoring1, mentoring2));
        mentor1.setMenteeList(List.of(mentoring1)); //양방향 for isApplied
        mentor2.setMenteeList(List.of(mentoring2));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mentee));

        List<FollowRequestListResponse> result = memberService.getMentorApplicationList(memberId);

        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getMemberId());
        assertEquals("mentor1", result.get(0).getNickname());
        assertEquals("Description 1", result.get(0).getDescription());
        assertEquals(true, result.get(0).isAccepted());

        assertEquals(11L, result.get(1).getMemberId());
        assertEquals("mentor2", result.get(1).getNickname());
        assertEquals("Description 2", result.get(1).getDescription());
        assertEquals(true, result.get(1).isAccepted());
    }

}
