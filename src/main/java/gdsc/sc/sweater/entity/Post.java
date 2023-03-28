package gdsc.sc.sweater.entity;

import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
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
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EntityListeners(value = AuditingEntityListener.class)

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title", length = 50)
    private String title;
    
    @Column(name = "content", length = 1000)
    private String content;

    
    @Column(name = "category")
    private int category;

    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Status status;


    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostScrap> postScrapList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImgUrl> postImgUrlList = new ArrayList<>();



    public static Post createPost(CreatePostRequest request, Member member) {
        Post post = new Post();
        post.setMember(member);
        post.setTitle(request.getTitle());
        post.setCategory(Integer.parseInt(request.getCategoryId()));
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setStatus(Status.ACTIVE);
        return post;
    }

    public static Post createTestPost(CreatePostRequest request, Member member) {
        Post post = new Post();
        post.setId(1L);
        post.setMember(member);
        post.setTitle(request.getTitle());
        post.setCategory(Integer.parseInt(request.getCategoryId()));
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setStatus(Status.ACTIVE);
        return post;
    }


}