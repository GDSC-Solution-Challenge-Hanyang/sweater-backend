package gdsc.sc.sweater;

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
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

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

    @Column(name = "status", length = 8)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "likeCount")
    private Long likeCount;

    @Column(name = "scrapCount")
    private Long scrapCount;
    
    @Column(name = "commentCount")
    private Long commentCount;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostScrap> postScrapList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImgUrl> postImgUrlList = new ArrayList<>();
}