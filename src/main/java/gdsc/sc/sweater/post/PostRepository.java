package gdsc.sc.sweater.post;

import gdsc.sc.sweater.entity.Post;
import gdsc.sc.sweater.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByCategoryAndStatus(int categoryId, Status status);

}
