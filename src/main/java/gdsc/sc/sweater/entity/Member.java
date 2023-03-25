package gdsc.sc.sweater.entity;

import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EntityListeners(value = AuditingEntityListener.class)

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;

    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private MemberRole role;


    public static Member createMember(CreateMemberRequest request) {
        Member member = new Member();
        member.setNickname(request.getNickName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPwd());
        member.setCreatedAt( LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        member.setStatus(Status.ACTIVE);
        member.setRole(request.getRole());

        return member;
    }

//    @OneToMany(mappedBy = "mentor")
//    private List<Mentoring> mentorList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "mentee")
//    private List<Mentoring> menteeList = new ArrayList<>();
}