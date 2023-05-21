package gdsc.sc.sweater.guide.dto;

import gdsc.sc.sweater.entity.GuideCategory;
import gdsc.sc.sweater.entity.GuideTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class GuideListResponse {

    private Long id;
    private String title;
    private String category;

    /**
     * 가이드 리스트
     */
    public GuideListResponse(GuideTitle guide, GuideCategory guideCategory) {
        this.id = guide.getId();
        this.title = guide.getTitle();
        this.category = guideCategory.getName();
    }





}
