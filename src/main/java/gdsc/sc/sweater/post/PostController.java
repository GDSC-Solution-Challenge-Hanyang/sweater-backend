package gdsc.sc.sweater.post;

import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

//    @PostMapping()
//    public CreatePostResponse createPost(@RequestBody CreatePostRequest request) {
//
//    }

}
