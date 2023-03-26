package gdsc.sc.sweater.post;

import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request, @RequestParam Long memberId) {
        CreatePostResponse post = postService.createPost(request, memberId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

}
