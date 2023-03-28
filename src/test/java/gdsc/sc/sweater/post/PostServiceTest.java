package gdsc.sc.sweater.post;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.entity.Comment;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.post.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static gdsc.sc.sweater.post.PostMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Test
    public void getPostList() {
        // given
        int category = 1;
        List<Post> postList = new ArrayList<>();
        Post post = Post.createTestPost(createPostRequest(), member); //with postId set
        postList.add(post);
        when(postRepository.findAllByCategoryAndStatus(category, Status.ACTIVE)).thenReturn(postList);

        // when
        List<PostListResponse> result = postService.getPostList(category);

        // then
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getPostId());
        assertEquals(post.getTitle(), result.get(0).getTitle());
        assertEquals(post.getContent(), result.get(0).getContent());
        verify(postRepository).findAllByCategoryAndStatus(category, Status.ACTIVE);
    }

    @Test
    public void getPostTest() {
        //given
        Post post = Post.createTestPost(createPostRequest(), member);
        Comment comment = Comment.createTestComment(post,member, createCommentRequest());
        post.setCommentListForTesting(Collections.singletonList(comment));

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));

        //when
        PostResponse postResponse = postService.getPost(1L);

        //then
        assertEquals(post.getId(), postResponse.getPostId());
        assertEquals(post.getTitle(), postResponse.getTitle());
        assertEquals(post.getContent(), postResponse.getContent());
        assertEquals(post.getMember().getNickname(), postResponse.getNickname());
        assertEquals(post.getPostLikeList().size(), postResponse.getLikeCount());
        assertEquals(post.getPostScrapList().size(), postResponse.getScrapCount());
        assertEquals(post.getCommentList().size(), postResponse.getCommentCount());
        assertEquals(String.valueOf(post.getCreatedAt()), postResponse.getCreatedAt());

        assertEquals(1, postResponse.getCommentDTOList().size());
        assertEquals(comment.getId(), postResponse.getCommentDTOList().get(0).getCommentId());
        assertEquals(comment.getMember().getNickname(), postResponse.getCommentDTOList().get(0).getNickname());
        assertEquals(comment.getContent(), postResponse.getCommentDTOList().get(0).getContent());
        assertEquals(String.valueOf(comment.getCreatedAt()), postResponse.getCommentDTOList().get(0).getCreatedAt());
    }



    @Test
    public void getPostNotFoundTest() {
        //given
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        //when, then
        assertThrows(CustomException.class, () -> postService.getPost(1L));
    }
}






