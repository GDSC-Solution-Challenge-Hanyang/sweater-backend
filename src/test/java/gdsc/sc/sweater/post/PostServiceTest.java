package gdsc.sc.sweater.post;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static gdsc.sc.sweater.post.PostRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;
    @MockBean
    private PostRepository postRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    public void MemberRepository가Null이아님() {
        assertThat(memberRepository).isNotNull();
    }


    @BeforeEach
    @Test
    @DisplayName("멤버 찾기 성공")
    void findMember() { //save 된 멤버를 id로 찾아옴.
        //given
        //saved 된 멤버가 이서야함
        Member member = Member.createMember(createMemberRequest());
        Member savedMember = memberRepository.save(member);
        Long memberId = savedMember.getId();

        //when
        Member findMember = postService.getMember(memberId);

        //then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());


    }

    @Test
    @DisplayName("멤버 찾기 실패")
    void findMemberFail() { //save 된 멤버를 id로 찾아옴.
        //given
        Long memberId = Long.MAX_VALUE; //saved 된 멤버가 없어야함

        //when
        final CustomException result = assertThrows(CustomException.class, () -> postService.getMember(memberId));

        //then
        assertThat(result.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    void createPost() {
        //given
        Long memberId = 1L;
        Member member = Member.createMember(createMemberRequest());
        CreatePostRequest request = createPostRequest();

        //when
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member)); //mock postRepository.findById()
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> { //mock postRepository.save()
            Post post = invocation.getArgument(0);
            post.setId(1L);
            return post;
        });
        CreatePostResponse response = postService.createPost(request, memberId);

        //then
        assertThat(response.getMemberId()).isEqualTo(memberId);
        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getContent()).isEqualTo(request.getContent());
        assertThat(response.getNickName()).isEqualTo(member.getNickname());

        verify(memberRepository).findById(memberId);
        verify(postRepository).save(any(Post.class));
    }


//    @DisplayName("게시물 생성")
//    @Test
//    void createPostWithOutMockito() {
//        //given
//        CreatePostRequest request = createPostRequest(); //parameter 1
//        List<Member> all = memberRepository.findAll();
//        Long memberId = all.get(0).getId(); //parameter 2
//        Member member = postService.getMember(memberId); //위 테스트에서 정상인것으로 테스트 완료
//
//        //when
//        CreatePostResponse postResponse = postService.createPost(createPostRequest(), memberId);
//
//        //then
//        Optional<Post> savedPost = postRepository.findById(postResponse.getPostId());
//
//        assertThat(postResponse.getPostId()).isEqualTo(savedPost.get().getId());
//        assertThat(postResponse.getMemberId()).isEqualTo(memberId);
//        assertThat(postResponse.getTitle()).isEqualTo(request.getTitle());
//        assertThat(postResponse.getContent()).isEqualTo(request.getContent());
//        assertThat(postResponse.getNickName()).isEqualTo(member.getNickname());
//    }

    //with MockBean

}
