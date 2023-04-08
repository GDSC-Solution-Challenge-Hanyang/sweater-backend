package gdsc.sc.sweater.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.sc.sweater.entity.Member;
import gdsc.sc.sweater.member.dto.CreateMemberRequest;
import gdsc.sc.sweater.member.dto.CreateMemberResponse;
import gdsc.sc.sweater.member.dto.FollowRequestListResponse;
import gdsc.sc.sweater.member.dto.MentorListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static gdsc.sc.sweater.member.MemberMock.memberRequest;
import static gdsc.sc.sweater.member.MemberMock.memberResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

//    @Autowired
//    private MemberController memberController;
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


    @Test
    public void getMentorListTest() throws Exception {
        Long memberId = 1L;
        List<MentorListResponse> mentorListResponse = Arrays.asList(
                new MentorListResponse(10L, "mentor1", "Description 1", false),
                new MentorListResponse(11L, "mentor2", "Description 2", true)
        );

        when(memberService.getMentorList(memberId)).thenReturn(mentorListResponse);

        mockMvc.perform(get("/member/mentor-list")
                        .param("memberId", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].memberId").value(10L))
                .andExpect(jsonPath("$[1].memberId").value(11L));
    }

    @Test
    public void getMentorApplicationListTest() throws Exception {
        //given
        Long memberId = 1L;
        List<FollowRequestListResponse> mentorApplicationListResponse = Arrays.asList(
                new FollowRequestListResponse(11L, "mentor2", "Description2", true)
        );

        //when
        when(memberService.getMentorApplicationList(memberId)).thenReturn(mentorApplicationListResponse);

        //then
        mockMvc.perform(get("/member/mentor-application-list")
                        .param("memberId", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().json(objectMapper.writeValueAsString(mentorApplicationListResponse)))
                .andExpect(jsonPath("$[0].memberId").value(11L));
    }

    @Test
    public void getMenteeApplicationListTest() throws Exception {
        Long memberId = 11L;

        List<FollowRequestListResponse> menteeApplicationListResponse = Arrays.asList(
                new FollowRequestListResponse(1L, "mentee1",null, true)
        );

        when(memberService.getMenteeApplicationList(memberId)).thenReturn(menteeApplicationListResponse);

        mockMvc.perform(get("/member/mentee-application-list")
                        .param("memberId", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(menteeApplicationListResponse)))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].memberId").value(1L));
    }

}

