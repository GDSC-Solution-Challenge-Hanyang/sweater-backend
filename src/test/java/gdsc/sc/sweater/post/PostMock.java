package gdsc.sc.sweater.post;

import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;

import java.time.LocalDateTime;

public class PostMock {

    static CreatePostRequest createPostRequest() {
        return CreatePostRequest.builder()
                .title("title")
                .content("content")
                .categoryId("1")
                .build();
    }

    static CreatePostResponse createPostResponse() {
        return CreatePostResponse.builder()
                .postId(1L)
                .memberId(1L)
                .nickName("nickName")
                .title("title")
                .content("content")
                .createdAt(LocalDateTime.now())
                .build();
    }


    static CreateMemberRequest createMemberRequest() {
        return CreateMemberRequest.builder()
                .nickName("nickName")
                .email("email")
                .pwd("pwd")
                .role(MemberRole.MENTEE)
                .build();
    }

}
