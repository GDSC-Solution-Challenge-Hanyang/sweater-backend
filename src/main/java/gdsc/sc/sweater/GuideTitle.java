package gdsc.sc.sweater;

import gdsc.sc.sweater.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "guide_title")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)

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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;

    @OneToMany(mappedBy = "guideTitle")
    private List<GuidePage> guidePageList = new ArrayList<>();


}