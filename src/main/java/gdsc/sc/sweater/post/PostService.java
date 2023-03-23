package gdsc.sc.sweater.post;

import gdsc.sc.sweater.Post;
import gdsc.sc.sweater.post.dto.CreatePostRequest;
import gdsc.sc.sweater.post.dto.CreatePostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

//    public CreatePostResponse createPost(CreatePostRequest request) {
//        Post post = Post.createPost(request);
//        Post save = postRepository.save(post);
//        postRepository.save(post);
//        return post;
//    }

}
