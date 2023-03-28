package gdsc.sc.sweater.post.dto;

import gdsc.sc.sweater.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentDTO {
    private Long commentId;
    private String nickname;
    private String content;
    private String createdAt;

    public CommentDTO(Comment c) {
        this.commentId = c.getId();
        this.nickname = c.getMember().getNickname();
        this.content = c.getContent();
        this.createdAt = String.valueOf(c.getCreatedAt());
    }
}
