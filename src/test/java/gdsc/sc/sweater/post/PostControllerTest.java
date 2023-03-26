package gdsc.sc.sweater.post;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import gdsc.sc.sweater.post.dto.CreatePostRequest;
//import gdsc.sc.sweater.post.dto.CreatePostResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//import static gdsc.sc.sweater.post.PostRepositoryTest.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static gdsc.sc.sweater.post.PostRepositoryTest.createPostRequest;
import static gdsc.sc.sweater.post.PostRepositoryTest.createPostResponse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@ExtendWith(SpringExtension.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class) //JUnit5와 Mockito를 /연동 https://mangkyu.tistory.com/145
//@AutoConfigureWebMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입받을 수 있음
public class PostControllerTest {


    @Autowired
    PostController postController;
    @MockBean
    private PostService postService;

//    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }



    @Test
    @DisplayName("게시물 생성 Controller")
    void createPost() throws Exception {
        //given
        Long memberId = 1L;
        CreatePostRequest request = createPostRequest();
        CreatePostResponse response = createPostResponse();

        //when
        when(postService.createPost(request, memberId)).thenReturn(response); //mock postService.createPost()

        //then
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); //LocalDateTime

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);

        mockMvc.perform(post("/post/posts")
                        .param("memberId", String.valueOf(memberId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
//                .andExpect(content().json(responseJson));
//                .andExpect(jsonPath("$.postId").exists())
                .andExpect(jsonPath("$.title").value(response.getTitle()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
//                .andExpect(jsonPath("$.memberId").value(request.getMemberId()))
                .andExpect(jsonPath("$.createdAt").exists());

        verify(postService).createPost(request, memberId);
    }



//    @Test
//    @DisplayName("게시물 생성")
//    void createPost() throws Exception {
//        //Mock 객체에서 특정 메소드가 실행되는 경우 실제 Return을 줄 수 없기 때문에 아래와 같이 가정 사항을 만들어줌
//        given(postService.createPost(createPostRequest(), 1L)).willReturn(
//                new CreatePostResponse(1L,1L,"nickName", "title", "content", LocalDateTime.now())); //LocalDateTime Now?
//
//        ProductDto productDto = ProductDto.builder().productId("15871").productName("pen")
//                .productPrice(5000).productStock(2000).build();
//        Gson gson = new Gson();
//        String content = gson.toJson(productDto);
//
//        // 아래 코드로 json 형태 변경 작업을 대체할 수 있음
//        // String json = new ObjectMapper().writeValueAsString(productDto);
//
//        mockMvc.perform(
//                        post("/api/v1/product-api/product")
//                                .content(content)
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productId").exists())
//                .andExpect(jsonPath("$.productName").exists())
//                .andExpect(jsonPath("$.productPrice").exists())
//                .andExpect(jsonPath("$.productStock").exists())
//                .andDo(print());
//
//        verify(productService).saveProduct("15871", "pen", 5000, 2000);
//    }



}
