package gdsc.sc.sweater.entity;

import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String description;



    @OneToMany(mappedBy = "mentee")
    private List<Mentoring> mentorList = new ArrayList<>();

    @OneToMany(mappedBy = "mentor") //mentor FK로 menteeId 컬럼을 찾는다는 뜻
    private List<Mentoring> menteeList = new ArrayList<>();


    public static Member createMemberByRequest(CreateMemberRequest request) {
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

    public static Member createTestMember(CreateMemberRequest request) {
        Member member = new Member();
        member.setId(1L);
        member.setNickname(request.getNickName());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPwd());
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        member.setStatus(Status.ACTIVE);
        member.setRole(request.getRole());
        return member;
    }

    public static Member createTestMemberWithId(Long id, String nickname, String email, String password, Status status, LocalDateTime createdAt, MemberRole role, String description, List<Mentoring> mentorList, List<Mentoring> menteeList) {
        Member member = new Member();
        member.setId(id);
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPassword(password);
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        member.setStatus(status);
        member.setRole(role);
        member.setDescription(description);
        member.setMenteeList(menteeList);
        member.setMentorList(mentorList);
        return member;
    }

    public static Member createTestMemberWithoutId(String nickname, String email, String password, Status status, LocalDateTime createdAt, MemberRole role, String description, List<Mentoring> mentorList, List<Mentoring> menteeList) {
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPassword(password);
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        member.setStatus(status);
        member.setRole(role);
        member.setDescription(description);
        member.setMenteeList(menteeList);
        member.setMentorList(mentorList);
        return member;
    }

}