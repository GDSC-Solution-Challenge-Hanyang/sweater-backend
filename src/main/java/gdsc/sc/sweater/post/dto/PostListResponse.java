package gdsc.sc.sweater.post.dto;

import gdsc.sc.sweater.entity.Comment;
import gdsc.sc.sweater.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponse {

    private Long postId;
    private String title;
    private String content;
    private String nickname;
    private int likeCount;
    private int commentCount;
    private int scrapCount;
    private String createdAt;

    public PostListResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getMember().getNickname();
        this.likeCount = post.getPostLikeList().size();
        this.scrapCount = post.getPostScrapList().size();
        this.commentCount = post.getCommentList().size();
        this.createdAt = String.valueOf(post.getCreatedAt());
    }

}
