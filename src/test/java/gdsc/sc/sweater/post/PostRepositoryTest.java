package gdsc.sc.sweater.post;

import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.enums.MemberRole;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * H2를 기반으로 테스트용 데이터베이스를 구축
 * 테스트가 끝나면 트랜잭션 롤백을 해준다. 레포지토리 계층은 실제 DB와 통신없이 단순 모킹하는 것은 의미가 없으므로 직접 데이터베이스와 통신하는 @DataJpaTest를 사용
 */
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void PostRepository가Null이아님() {
        assertThat(postRepository).isNotNull();
    }


    @BeforeEach
    @Test
    void createMember() {
        Member member = Member.createMemberByRequest(memberRequest());

    }

    @Test
    @DisplayName("게시물 생성")
    void createPost() {
        //given
        Member member = Member.createMemberByRequest(memberRequest());
        Post post = Post.createPost(postRequest(), member);

        //when
        Member savedMember = memberRepository.save(member);
        Post savedPost = postRepository.save(post);

        //then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getRole()).isEqualTo(member.getRole());


        assertThat(savedMember).isEqualTo(member);
        assertThat(savedPost).isEqualTo(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getMember()).isEqualTo(post.getMember());
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getCategory()).isEqualTo(post.getCategory());
        assertThat(savedPost.getContent()).isEqualTo(post.getContent());
    }

    private CreatePostRequest postRequest() {
        return CreatePostRequest.builder()
                .title("title")
                .content("content")
                .categoryId(1)
                .build();
    }

    static CreateMemberRequest memberRequest() {
        return CreateMemberRequest.builder()
                .nickName("nickName")
                .email("email")
                .pwd("pwd")
                .role(MemberRole.MENTEE)
                .build();
    }
}
