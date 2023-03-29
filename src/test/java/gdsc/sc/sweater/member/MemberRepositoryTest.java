package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.MentoringStatus;
import gdsc.sc.sweater.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static gdsc.sc.sweater.member.MemberMock.memberRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member mentor1;
    private Member mentor2;
    private Member mentee;


    @Test
    public void MemberRepositoryIsNotNull(){
        assertThat(memberRepository).isNotNull();
    }

    @BeforeEach
    void setMentorAndMentee() {
        mentor1 = Member.createTestMemberWithoutId( "mentor1", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 1", null, null);
        mentor2 = Member.createTestMemberWithoutId("mentor2", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 2", null, null);
        mentee = Member.createTestMemberWithoutId("mentee", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTEE, "Description 3", null, null);

        Mentoring mentoring1 = Mentoring.createMentoringForTest(1L, mentee, mentor1, MentoringStatus.PENDING);
        Mentoring mentoring2 = Mentoring.createMentoringForTest(2L, mentee, mentor2, MentoringStatus.PENDING);
        mentee.setMentorList(Arrays.asList(mentoring1, mentoring2));
        mentor1.setMenteeList(List.of(mentoring1)); //양방향 for isApplied
        mentor2.setMenteeList(List.of(mentoring2));

        //when
        memberRepository.save(mentor1);
        memberRepository.save(mentor2);
        memberRepository.save(mentee);
    }


    @Test
    @DisplayName("맴버 생성")
    void create(){
        //given
        Member member = Member.createMemberByRequest(memberRequest());
        //when
        memberRepository.save(member);
        //then
        Member savedMember = memberRepository.findById(member.getId()).get();
        assertEquals("입력한 맴버와 저장된 맴버 비교", member, savedMember);
    }

    @Test
    public void findAllByRoleAndStatusTest() {
        //given
        List<Member> mentors = memberRepository.findAllByRoleAndStatus(MemberRole.MENTOR, Status.ACTIVE);
//        for (Member mentor : mentors) {
//            System.out.println("mentor = " + mentor.getId());
//        }

        //then
        Assertions.assertEquals(5, mentors.size());
        Member foundMentor1 = mentors.stream().filter(m -> m.getId().equals(mentor1.getId())).findAny().get();
        Member foundMentor2 = mentors.stream().filter(m -> m.getId().equals(mentor2.getId())).findAny().get();

        Assertions.assertEquals(mentor1.getId(), foundMentor1.getId());
        Assertions.assertEquals(mentor2.getId(), foundMentor2.getId());

        assertTrue(mentors.stream().anyMatch(m -> m.getId().equals(mentor1.getId())));
        assertTrue(mentors.stream().anyMatch(m -> m.getId().equals(mentor2.getId())));
    }
}
