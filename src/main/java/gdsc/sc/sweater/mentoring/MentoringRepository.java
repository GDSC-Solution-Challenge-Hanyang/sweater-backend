package gdsc.sc.sweater.mentoring;

import gdsc.sc.sweater.entity.Mentoring;
import gdsc.sc.sweater.enums.MentoringStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring,Long> {

    Mentoring findMentoringByMenteeIdAndMentorId(Long menteeId, Long mentorId);

    Mentoring findMentoringByMenteeIdAndMentorIdAndStatus(Long menteeId, Long mentorId, MentoringStatus mentoringStatus);

}
