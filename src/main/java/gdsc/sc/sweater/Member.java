package gdsc.sc.sweater;

import gdsc.sc.sweater.enums.MemberRole;
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
@Table(name = "member")
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
    private Status status;

    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "role")
    private MemberRole role;

    @OneToMany(mappedBy = "mentor")
    private List<Mentoring> mentorList = new ArrayList<>();

    @OneToMany(mappedBy = "mentee")
    private List<Mentoring> menteeList = new ArrayList<>();
}