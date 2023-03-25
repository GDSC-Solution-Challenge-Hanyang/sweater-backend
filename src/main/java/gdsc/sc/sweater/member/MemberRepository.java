package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
