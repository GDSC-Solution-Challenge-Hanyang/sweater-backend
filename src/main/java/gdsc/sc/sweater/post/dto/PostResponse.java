package gdsc.sc.sweater.post.dto;

import gdsc.sc.sweater.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String nickname;
    private int likeCount;
    private int scrapCount;
    private int commentCount;
    private String createdAt;
    private List<CommentDTO> commentDTOList;
//    private List<String> postImgUrl;

    public PostResponse(Post p, List<CommentDTO> c) {
        postId = p.getId();
        title = p.getTitle();
        content = p.getContent();
        nickname = p.getMember().getNickname();
        likeCount = p.getPostLikeList().size();
        scrapCount = p.getPostScrapList().size();
        commentCount = c.size();
        commentDTOList = c;
        createdAt = String.valueOf(p.getCreatedAt());
    }
}
