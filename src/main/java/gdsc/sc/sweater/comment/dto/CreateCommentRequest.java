package gdsc.sc.sweater.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCommentRequest {

    private Long postId;
    private String content;

}
