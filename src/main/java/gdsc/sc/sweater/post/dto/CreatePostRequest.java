package gdsc.sc.sweater.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreatePostRequest {

    private String title;
    private String content;
    private int categoryId;

//    private List<MultipartFile> multipartFile;


}
