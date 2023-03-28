package gdsc.sc.sweater.post;

import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import gdsc.sc.sweater.post.dto.PostListResponse;
import gdsc.sc.sweater.post.dto.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 생성
     */
    @PostMapping("")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request, @RequestParam Long memberId) {
        CreatePostResponse post = postService.createPost(request, memberId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    /**
     * 게시물 리스트
     */
    @GetMapping("")
    public ResponseEntity<List<PostListResponse>> getPostList(@RequestParam int category) {
        List<PostListResponse> postList = postService.getPostList(category);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }


    /**
     * 게시물 상세
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
        PostResponse post = postService.getPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

}
