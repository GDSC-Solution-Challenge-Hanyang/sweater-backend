package gdsc.sc.sweater.common.exception2;

import gdsc.sc.sweater.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Member, Long> {
}
