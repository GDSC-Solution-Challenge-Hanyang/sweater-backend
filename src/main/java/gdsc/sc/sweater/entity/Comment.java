package gdsc.sc.sweater.entity;

import gdsc.sc.sweater.comment.dto.CreateCommentRequest;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter @Setter
@Entity
@Table(name = "comment")
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "content")
    private String content;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;

    public static Comment createTestComment(Post post, Member member, CreateCommentRequest request) {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPost(post);
        comment.setMember(member);
        comment.setContent(request.getContent());
        comment.createdAt = LocalDateTime.now();
        comment.updatedAt = LocalDateTime.now();
        comment.status = Status.ACTIVE;
        return comment;
    }

}