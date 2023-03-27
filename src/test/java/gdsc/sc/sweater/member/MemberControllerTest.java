package gdsc.sc.sweater.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.CreateMemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static gdsc.sc.sweater.member.MemberMock.memberRequest;
import static gdsc.sc.sweater.member.MemberMock.memberResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @Autowired
    private MemberController memberController;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("맴버 생성 controller")
    void create() throws Exception {
        //given
        CreateMemberRequest createMemberRequest = memberRequest();
        CreateMemberResponse createMemberResponse = memberResponse();
        Member member = Member.createMemberByRequest(createMemberRequest);
        member.setId(1L);

        //when
        when(memberService.create(any(CreateMemberRequest.class))).thenReturn(member);

        //then
        mockMvc.perform(post("/member/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMemberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(createMemberResponse.getEmail()));


    }


}

