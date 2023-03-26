package gdsc.sc.sweater.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class CreatePostRequest {

    private String title;
    private String content;
    private String categoryId;

//    private List<MultipartFile> multipartFile;


}
