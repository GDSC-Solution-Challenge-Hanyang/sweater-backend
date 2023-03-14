package gdsc.sc.sweater;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name="guide_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)

public class GuideCategory {


    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}

