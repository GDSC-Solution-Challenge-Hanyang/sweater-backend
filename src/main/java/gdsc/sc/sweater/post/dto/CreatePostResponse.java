package gdsc.sc.sweater.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class CreatePostResponse {

     String postId;
     String memberId;
     String nickName;
     String title;
     String content;
     LocalDateTime createdAt;

//    @Builder
//    public CreatePostResponse(Long memberId, String nickName, String title, String content, LocalDateTime createdAt) {
//        this.memberId = memberId;
//        this.nickName = nickName;
//        this.title = title;
//        this.content = content;
//        this.createdAt = createdAt;
//    }
}
