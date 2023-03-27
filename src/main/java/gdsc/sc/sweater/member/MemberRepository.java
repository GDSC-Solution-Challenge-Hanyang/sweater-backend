package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByEmail(String email);
}
