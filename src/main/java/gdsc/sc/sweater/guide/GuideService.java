package gdsc.sc.sweater.guide;

import gdsc.sc.sweater.entity.GuideCategory;
import gdsc.sc.sweater.entity.GuideTitle;
import gdsc.sc.sweater.guide.dto.GuideListResponse;
import gdsc.sc.sweater.guide.dto.GuidePageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class GuideService {

    private final GuideTitleRepository guideTitleRepository;
    private final GuideCategoryRepository guideCategoryRepository;

    public List<GuideListResponse> getHomeGuideList(int categoryId) {
        List<GuideTitle> homeGuideList = guideTitleRepository.findAllByOrderByCreatedAt();
        GuideCategory guideCategory = guideCategoryRepository.findById(categoryId).get();
        return homeGuideList.stream()
                .map(guide -> new GuideListResponse(guide, guideCategory))
                .collect(Collectors.toList());
    }

    public List<GuideListResponse> getGuideList(int categoryId) {
        GuideCategory guideCategory = guideCategoryRepository.findById(categoryId).get();
        List<GuideTitle> guideList = guideTitleRepository.findGuideTitlesByCategory(categoryId);
        return guideList.stream()
                .map(guide -> new GuideListResponse(guide, guideCategory))
                .collect(Collectors.toList());
    }

    public GuidePageResponse getGuide(Long guideId) {
        GuideTitle guide = guideTitleRepository.findById(guideId).get();
        return new GuidePageResponse(guide);
    }
}
