package gdsc.sc.sweater.guide.dto;

import gdsc.sc.sweater.entity.GuidePage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuidePageListDTO {

    private Long guidePageId;
    private String subtitle;
    private String content;

    public GuidePageListDTO(GuidePage guidePage) {
        this.guidePageId = guidePage.getId();
        this.subtitle = guidePage.getSubtitle();
        this.content = guidePage.getContent();
    }
}
