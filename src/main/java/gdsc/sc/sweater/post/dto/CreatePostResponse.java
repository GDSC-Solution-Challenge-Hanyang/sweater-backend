package gdsc.sc.sweater.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CreatePostResponse {

    private Long memberId;
    private String nickName;
    private String title;
    private String content;
    private LocalDateTime createdAt;

//    @Builder
//    public CreatePostResponse(Long memberId, String nickName, String title, String content, LocalDateTime createdAt) {
//        this.memberId = memberId;
//        this.nickName = nickName;
//        this.title = title;
//        this.content = content;
//        this.createdAt = createdAt;
//    }
}
