package gdsc.sc.sweater.post;

import gdsc.sc.sweater.comment.dto.CreateCommentRequest;
import gdsc.sc.sweater.entity.Comment;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.post.dto.*;

import java.time.LocalDateTime;
import java.util.Collections;

public class PostMock {

    static CreatePostRequest createPostRequest() {
        return CreatePostRequest.builder()
                .title("title")
                .content("content")
                .categoryId("1")
                .build();
    }

    static CreatePostRequest createPostRequestWithDiffCategory() {
        return CreatePostRequest.builder()
                .title("title")
                .content("content")
                .categoryId("2")
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

    static PostListResponse postListResponse() {
        return PostListResponse.builder()
                .postId(1L)
                .title("Title 1")
                .content("Content 1")
                .nickname("Nickname 1")
                .likeCount(1)
                .commentCount(1)
                .scrapCount(1)
                .createdAt(String.valueOf(LocalDateTime.now()))
                .build();
    }

    static CreateCommentRequest createCommentRequest() {
        return CreateCommentRequest.builder()
                .postId(1L)
                .content("content")
                .build();
    }

//    static PostResponse postResponse() {
//        return PostResponse.builder()
//                .postId(1L)
//                .title("title")
//                .content("content")
//                .nickname("nickName")
//                .likeCount(0)
//                .scrapCount(0)
//                .commentCount(1)
//                .commentDTOList(Collections.singletonList(new CommentDTO()))
//                .build();
//    }

    //        postId = p.getId();
    //        title = p.getTitle();
    //        content = p.getContent();
    //        nickname = p.getMember().getNickname();
    //        likeCount = p.getPostLikeList().size();
    //        scrapCount = p.getPostScrapList().size();
    //        commentCount = c.size();
    //        commentDTOList = c;
    //        createdAt = String.valueOf(p.getCreatedAt());


}
