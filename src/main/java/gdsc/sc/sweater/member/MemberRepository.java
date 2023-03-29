package gdsc.sc.sweater.member;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMemberByEmail(String email);

    List<Member> findAllByRoleAndStatus(MemberRole role, Status status);

}
