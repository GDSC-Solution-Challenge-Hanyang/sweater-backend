package gdsc.sc.sweater.post;

import gdsc.sc.sweater.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
