package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import static gdsc.sc.sweater.member.MemberMock.memberRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void MemberRepositoryIsNotNull(){
        assertThat(memberRepository).isNotNull();
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





}
