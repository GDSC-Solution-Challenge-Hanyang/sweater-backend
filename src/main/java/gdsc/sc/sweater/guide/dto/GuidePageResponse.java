package gdsc.sc.sweater.guide.dto;

import gdsc.sc.sweater.entity.GuidePage;
import gdsc.sc.sweater.entity.GuideTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GuidePageResponse {

    private Long guideId;
    private String title;
    private List<GuidePageListDTO> guidePageListDTO;


    public GuidePageResponse(GuideTitle guide) {
        List<GuidePage> guidePageList = guide.getGuidePageList();
        this.guideId = guide.getId();
        this.title = guide.getTitle();
        this.guidePageListDTO = guidePageList.stream()
                .map(guidePage -> new GuidePageListDTO(guidePage))
                .collect(Collectors.toList());
    }
}
