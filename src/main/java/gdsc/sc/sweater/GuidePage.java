package gdsc.sc.sweater;

import gdsc.sc.sweater.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "guide_page")
public class GuidePage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id")
    private GuideTitle guideTitle;
    
    @Column(name = "page")
    private Long page;

    @Column(name = "subtitle", length = 50)
    private String subtitle;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private Status status;
    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "related_organization")
    private String relatedOrganization;

}