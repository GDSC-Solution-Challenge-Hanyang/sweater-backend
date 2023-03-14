package gdsc.sc.sweater;

import gdsc.sc.sweater.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "guide_title")
public class GuideTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "category")
    private int category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status", length = 8)
    @Enumerated(EnumType.STRING)

    private Status status;

    @OneToMany(mappedBy = "guideTitle")
    private List<GuidePage> guidePageList = new ArrayList<>();


}