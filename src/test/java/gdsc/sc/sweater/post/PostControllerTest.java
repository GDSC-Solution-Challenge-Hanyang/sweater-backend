package gdsc.sc.sweater.post;

import com.google.gson.Gson;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(PostController.class) //https://frozenpond.tistory.com/82
@ExtendWith(MockitoExtension.class) //JUniit5와 Mockito를 연동 https://mangkyu.tistory.com/145
public class PostControllerTest {

//    @Autowired //https://frozenpond.tistory.com/82
//    MockMvc mvc;
//
//    @MockBean //https://frozenpond.tistory.com/82
//    PostService postService;
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    private PostController postController; //망나니개발자 mockMVC?
    @Mock
    private PostService postService;


    @DisplayName("게시물 생성 테스트")
    @Test
    void createPost(){
        //given
        CreatePostRequest req = CreatePostRequest.builder()
                .title("제목")
                .content("본문")
                .build();
        Gson gson = new Gson();
        String content = gson.toJson(req);

//        mockMvc.perform(post("/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(req))
//                .andExpect(status().isCreated());
//
//        //when
//        postService.createPost(req);
//
//        //then
//
//        assertThat()
    }

    @DisplayName("회원 가입 성공") //망나니개발자
    @Test
    void createPostSuccess() throws Exception {
        // given
        CreatePostRequest request = createPostRequest();
        CreatePostResponse response = createPostResponse();

        doReturn(response).when(postService)
                .createPost(any(CreatePostRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        //then
//        MvcResult mvcResult = resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("memberId"),response.getMemberId())
//                .andExpect(jsonPath("memberId", response.getMemberId()).exists())
//                .andExpect(jsonPath("title", response.getTitle()).exists())
//                .andExpect(jsonPath("pw", response.getContent()).exists())
//                .andExpect(jsonPath("memberId", response.getMemberId()).exists());
    }

    private CreatePostRequest createPostRequest() {
        return CreatePostRequest.builder()
                .title("제목")
                .content("본문")
                .build();
    }

    private CreatePostResponse createPostResponse() {
        return CreatePostResponse.builder()
                .memberId(1L)
                .nickName("nickName")
                .content("content")
                .title("title")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
