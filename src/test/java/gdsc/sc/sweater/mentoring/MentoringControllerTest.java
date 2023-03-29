package gdsc.sc.sweater.mentoring;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.sc.sweater.post.PostController;
import gdsc.sc.sweater.post.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MentoringController.class)
public class MentoringControllerTest {

    @Autowired
    private MentoringController mentoringController;

    @MockBean
    private MentoringService mentoringService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void applyMentoring() throws Exception {
        when(mentoringService.applyMentoring(anyLong(), anyLong())).thenReturn("success");

        mockMvc.perform(post("/mentoring/status")
                        .param("memberId", "1")
                        .param("mentorId", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void acceptMentoringRequest() throws Exception {
        when(mentoringService.acceptMentoringRequest(anyLong(), anyLong())).thenReturn("success");

        mockMvc.perform(patch("/mentoring/status")
                        .param("memberId", "2")
                        .param("menteeId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}