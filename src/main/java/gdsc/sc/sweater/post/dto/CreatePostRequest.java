package gdsc.sc.sweater.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePostRequest {

    private String title;
    private String content;

    @Builder
    public CreatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
