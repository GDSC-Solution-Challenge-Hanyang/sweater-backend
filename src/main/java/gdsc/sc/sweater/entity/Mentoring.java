package gdsc.sc.sweater.entity;

import gdsc.sc.sweater.enums.MentoringStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "mentoring")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EntityListeners(value = AuditingEntityListener.class)

public class Mentoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Member mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    private Member mentee;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private MentoringStatus status;
    
    public static Mentoring applyMentoring(Member mentee, Member mentor) {
        Mentoring mentoring = new Mentoring();
        mentoring.setMentor(mentor);
        mentoring.setMentee(mentee);
        mentoring.setCreatedAt( LocalDateTime.now());
        mentoring.setUpdatedAt(LocalDateTime.now());
        mentoring.setStatus(MentoringStatus.PENDING);
        return mentoring;
    }

    public void modifyStatusAsMatched() {
        this.status = MentoringStatus.MATCHED;
    }

    public static Mentoring createMentoringForTest(Long pk, Member mentee, Member mentor, MentoringStatus status) {
        Mentoring mentoring = new Mentoring();
        mentoring.setId(pk);
        mentoring.setMentor(mentor);
        mentoring.setMentee(mentee);
        mentoring.setCreatedAt(LocalDateTime.now());
        mentoring.setUpdatedAt(LocalDateTime.now());
        mentoring.setStatus(status);
        return mentoring;
    }


}