package gdsc.sc.sweater;

import gdsc.sc.sweater.enums.MemberRole;
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
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "mentor_id")
    private Long mentorId;

    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "mentor")
    private List<Mentoring> mentorList = new ArrayList<>();

    @OneToMany(mappedBy = "mentee")
    private List<Mentoring> menteeList = new ArrayList<>();
}