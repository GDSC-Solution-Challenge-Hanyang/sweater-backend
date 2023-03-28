package gdsc.sc.sweater.post;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.post.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static gdsc.sc.sweater.post.PostMock.createMemberRequest;
import static gdsc.sc.sweater.post.PostMock.createPostRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;
    @MockBean
    private PostRepository postRepository;
    @MockBean
    private MemberRepository memberRepository;

    private CreatePostRequest request;
    private Member member;

    @BeforeEach
    @Test
    void setUp() {
        //given
        request = createPostRequest();
        member = Member.createTestMember(createMemberRequest()); //set memberId=1L
    }

    @Test
    void createPost() {
        //given
//        Member member = Member.createTestMember(createMemberRequest()); //with memberId=1L
//        CreatePostRequest request = createPostRequest();

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member)); //mock postRepository.findById()
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> { //mock postRepository.save()
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1L);
            return savedPost;
        });

        //when
        CreatePostResponse response = postService.createPost(request, member.getId());

        //then
        assertEquals(1L, response.getPostId());
        assertEquals(member.getId(), response.getMemberId());
        assertEquals(member.getNickname(), response.getNickName());
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getContent(), response.getContent());

        verify(memberRepository).findById(member.getId());
        verify(postRepository).save(any(Post.class));
    }
}
