package gdsc.sc.sweater.mentoring;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.MentoringStatus;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MentoringServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MentoringRepository mentoringRepository;
    @InjectMocks
    private MentoringService mentoringService;

    private Member mentor;
    private Member mentee;


    @BeforeEach
    void setMentorAndMentee() {
        mentor = Member.createTestMemberWithId(1L, "mentor", "email", "pwd", Status.ACTIVE, LocalDateTime.now(), MemberRole.MENTOR, "Description 1", null, null);
        mentee = Member.createTestMemberWithId(2L, "mentee", "email", "pwd", Status.ACTIVE, LocalDateTime.now(), MemberRole.MENTEE, "Description 2", null, null);
    }


    @Test
    void applyMentoringTest() {
        //given mentor, mentee

        //when
        Mentoring mentoring = Mentoring.applyMentoring(mentee, mentor);

        //then
        assertEquals(mentor, mentoring.getMentor());
        assertEquals(mentee, mentoring.getMentee());
        assertEquals(MentoringStatus.PENDING, mentoring.getStatus());
    }


    @Test
    void applyMentoringServiceTest() {
        // Given mentor, mentee
        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(mentor));
        when(memberRepository.findById(eq(2L))).thenReturn(Optional.of(mentee));
//      when(mentoringRepository.save(any(Mentoring.class))).thenReturn(any(Mentoring.class));
        when(mentoringRepository.save(any(Mentoring.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        String result = mentoringService.applyMentoring(2L, 1L);

        // Then
        assertEquals("success", result);

        verify(memberRepository).findById(eq(1L));
        verify(memberRepository).findById(eq(2L));
        verify(mentoringRepository).save(any(Mentoring.class));
    }


    @Test
    void acceptMentoringRequestTest() {
        //given
        Mentoring mentoring = Mentoring.applyMentoring(mentee, mentor);

        when(mentoringRepository.findMentoringByMenteeIdAndMentorId(2L,1L)).thenReturn(mentoring);

        //when
        String result = mentoringService.acceptMentoringRequest(1L, 2L);

        //then
        assertEquals("success", result);
        assertEquals(MentoringStatus.MATCHED, mentoring.getStatus());
        verify(mentoringRepository).findMentoringByMenteeIdAndMentorId(anyLong(), anyLong());
    }
}
