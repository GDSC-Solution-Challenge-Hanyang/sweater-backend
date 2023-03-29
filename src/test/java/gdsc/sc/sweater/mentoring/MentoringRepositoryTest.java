package gdsc.sc.sweater.mentoring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MentoringRepositoryTest {

    @Autowired
    private MentoringRepository mentoringRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Member mentor;
    private Member mentee;


    @BeforeEach
    void setMentorAndMentee() {
        mentor = Member.createTestMemberWithoutId( "mentor", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTOR, "Description 1", null, null);
        mentee = Member.createTestMemberWithoutId("mentee", "email", "pwd", Status.ACTIVE, null, MemberRole.MENTEE, "Description 2", null, null);
    }


    @Test
    void findMentoringByMenteeIdAndMentorId() {
        // Given
        Member savedMentee = memberRepository.save(mentee);
        Member savedMentor = memberRepository.save(mentor);

        Mentoring mentoring = Mentoring.applyMentoring(savedMentee, savedMentor);
        Mentoring savedMentoring = mentoringRepository.save(mentoring);

        // When
        Optional<Mentoring> foundMentoring = Optional.ofNullable(mentoringRepository.findMentoringByMenteeIdAndMentorId(savedMentee.getId(), savedMentor.getId()));

        // Then
        assertThat(foundMentoring.isPresent()).isTrue();
        assertThat(foundMentoring.get().getMentee()).isEqualTo(savedMentee);
        assertThat(foundMentoring.get().getMentor()).isEqualTo(savedMentor);
    }
}
