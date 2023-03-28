package gdsc.sc.sweater.post;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gdsc.sc.sweater.post.PostMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

/**
 * 테스트가 끝나면 트랜잭션 롤백을 해준다. 레포지토리 계층은 실제 DB와 통신없이 단순 모킹하는 것은 의미가 없으므로 직접 데이터베이스와 통신하는 @DataJpaTest를 사용
 */
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Member member;


    @Test
    public void PostRepositoryIsNotNull() {
        assertThat(postRepository).isNotNull();
    }


    @BeforeEach
    @Test
    void createMember() {
        member = Member.createTestMember(createMemberRequest());
    }

    @Test
    @DisplayName("게시물 저장")
    void savePost() {
        //given
        Member member = Member.createTestMember(createMemberRequest()); //memberId = 1L
        Post post = Post.createPost(createPostRequest(), member);

        //when
        Post savedPost = postRepository.save(post);

        //then
        assertNotNull(savedPost);
        assertNotNull(savedPost.getId());
        assertEquals(post.getMember(), savedPost.getMember());
        assertEquals(post.getTitle(), savedPost.getTitle());
        assertEquals(post.getContent(), savedPost.getContent());
        assertEquals(post.getCategory(), savedPost.getCategory());
        assertNotNull(savedPost.getCreatedAt());
        assertEquals(member, savedPost.getMember());
    }

    @Test
    void findAllByCategoryAndStatus() {
        // given
        int categoryId = 1;
        postRepository.save(Post.createPost(createPostRequest(),member)); // Add Post instances to the database
        postRepository.save(Post.createPost(createPostRequestWithDiffCategory(),member));

        // when
        List<Post> posts = postRepository.findAllByCategoryAndStatus(categoryId, Status.ACTIVE);

        // then
        assertThat(posts).isNotEmpty(); // Modify this check according to your test data
        assertThat(posts.size()).isEqualTo(1);

        assertNotNull(posts.get(0).getId());
        assertEquals(posts.get(0).getMember().getId(), member.getId());
        assertEquals(posts.get(0).getCategory(), categoryId);
    }

}
