package gdsc.sc.sweater.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gdsc.sc.sweater.entity.Comment;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.post.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gdsc.sc.sweater.post.PostMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
@ExtendWith(MockitoExtension.class) //JUnit5와 Mockito를 연동. 145
public class PostControllerTest {

    @Autowired
    PostController postController;
    @MockBean
    private PostService postService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("게시물 생성 Controller")
    void createPost() throws Exception {
        //given
        Long memberId = 1L;
        CreatePostRequest request = createPostRequest();
        CreatePostResponse response = createPostResponse();

        //when
//        when(postService.createPost(any(CreatePostRequest.class),any(Long.class))).thenReturn(response); //mock postService.createPost()
        when(postService.createPost(any(CreatePostRequest.class), eq(memberId))).thenReturn(response);

        //then
        objectMapper.registerModule(new JavaTimeModule()); //LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);

        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .param("memberId", String.valueOf(memberId))
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.postId").exists())
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.nickName").value(response.getNickName()))
                .andExpect(jsonPath("$.title").value(response.getTitle()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andDo(print());

        verify(postService).createPost(any(CreatePostRequest.class), eq(memberId));
    }

    @Test
    void getPostList() throws Exception {
        // given
        int category = 1;
        List<PostListResponse> postList = new ArrayList<>();
        postList.add(postListResponse());

        // when
        when(postService.getPostList(category)).thenReturn(postList);

        // then
        mockMvc.perform(get("/post")
                        .param("category", String.valueOf(category))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postList)))
                .andDo(print());
    }


    @Test
    public void getPostDetailTest() throws Exception {
        //given
        Member member = Member.createTestMember(createMemberRequest());
        Post post = Post.createTestPost(createPostRequest(), member);
        Comment comment = Comment.createTestComment(post, member, createCommentRequest());
        post.setCommentListForTesting(Collections.singletonList(comment));

        PostResponse postResponse = new PostResponse(post, Collections.singletonList(new CommentDTO(comment)));

        //when
        when(postService.getPost(any(Long.class))).thenReturn(postResponse);

        //then
        mockMvc.perform(get("/post/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postResponse)))
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andExpect(jsonPath("$.nickname").value("nickName"))
                .andExpect(jsonPath("$.commentCount").value(1))
                .andExpect(jsonPath("$.commentDTOList[0].commentId").value(1L))
                .andExpect(jsonPath("$.commentDTOList[0].nickname").value("nickName"))
                .andExpect(jsonPath("$.commentDTOList[0].content").value("content"));
    }

}
