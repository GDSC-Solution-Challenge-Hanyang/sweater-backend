package gdsc.sc.sweater.post;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import gdsc.sc.sweater.post.dto.PostListResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static gdsc.sc.sweater.common.exception.ErrorCode.EMPTY_POST_LIST;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시물 생성
     */
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request, Long memberId) {
        Member member = getMember(memberId);
        Post savedPost = postRepository.save(Post.createPost(request, member));

        return CreatePostResponse.builder()
                .postId(savedPost.getId())
                .memberId(savedPost.getMember().getId())
                .nickName(savedPost.getMember().getNickname())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .createdAt(savedPost.getCreatedAt())
                .build();
    }

    Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }


    /**
     * 게시물 리스트
     */
    public List<PostListResponse> getPostList(int category) {
        List<Post> postList = postRepository.findAllByCategoryAndStatus(category, Status.ACTIVE);
        if (postList.size() == 0) {
            throw new CustomException(EMPTY_POST_LIST);
        }
        return postList.stream()
                .map(p -> PostListResponse.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .nickname(p.getMember().getNickname())
                        .likeCount(p.getPostLikeList().size())
                        .commentCount(p.getCommentList().size())
                        .scrapCount(p.getPostScrapList().size())
                        .createdAt(String.valueOf(p.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }
}
