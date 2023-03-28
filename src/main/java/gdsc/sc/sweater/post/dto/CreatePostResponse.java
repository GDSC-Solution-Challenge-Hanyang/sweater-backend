package gdsc.sc.sweater.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostResponse {

     Long postId;
     Long memberId;
     String nickName;
     String title;
     String content;
     LocalDateTime createdAt;

}
