package gdsc.sc.sweater.guide;

import gdsc.sc.sweater.entity.GuideTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideTitleRepository extends JpaRepository<GuideTitle, Long> {

    List<GuideTitle> findGuideTitlesByCategory(int categoryId);

    List<GuideTitle> findAllByOrderByCreatedAt();


}
