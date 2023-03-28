package gdsc.sc.sweater.post;

import gdsc.sc.sweater.common.exception.CustomException;
import gdsc.sc.sweater.common.exception.ErrorCode;
import gdsc.sc.sweater.entity.Comment;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.enums.Status;
import gdsc.sc.sweater.member.MemberRepository;
import gdsc.sc.sweater.post.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static gdsc.sc.sweater.common.exception.ErrorCode.*;

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
                .map(p -> new PostListResponse(p))
                .collect(Collectors.toList());
    }

    /**
     * 게시물 상세
     */
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        List<Comment> commentList = post.getCommentList();
        List<CommentDTO> commentDTOList = commentList.stream()
                .map(c -> new CommentDTO(c))
                .collect(Collectors.toList());
        return new PostResponse(post, commentDTOList);
    }
}
