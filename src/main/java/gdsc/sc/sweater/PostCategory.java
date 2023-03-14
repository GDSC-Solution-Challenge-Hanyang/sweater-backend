package gdsc.sc.sweater;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="post_category")

public class PostCategory {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
