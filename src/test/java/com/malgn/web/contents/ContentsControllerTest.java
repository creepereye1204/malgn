package com.malgn.web.contents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.malgn.domain.contents.Contents;
import com.malgn.service.contents.ContentsService;
import com.malgn.service.monitoring.SlackNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.malgn.web.GlobalExceptionHandler;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ContentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContentsService contentsService;

    @Mock
    private SlackNotifier slackNotifier;

    @InjectMocks
    private ContentsController contentsController;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contentsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler(slackNotifier))
                .build();
    }

    @Test
    void create_Success() throws Exception {
        ContentsRequest request = new ContentsRequest();
        request.setTitle("Test Title");
        request.setDescription("Test Description");

        given(contentsService.create(anyString(), anyString())).willReturn(Contents.builder().title("T").description("D").build());

        mockMvc.perform(post("/api/contents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void findAll_Paging() throws Exception {
        given(contentsService.findAll(any(Pageable.class))).willReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(get("/api/contents")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void detail_Success() throws Exception {
        Contents contents = Contents.builder().title("Post").description("Desc").build();
        given(contentsService.findById(any())).willReturn(contents);

        mockMvc.perform(get("/api/contents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Post"));
    }

    @Test
    void update_Forbidden_WhenNotOwner() throws Exception {
        ContentsRequest request = new ContentsRequest();
        request.setTitle("Title");
        request.setDescription("Desc");

        doThrow(new AccessDeniedException("Forbidden")).when(contentsService).update(anyLong(), anyString(), anyString());

        mockMvc.perform(put("/api/contents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete_Forbidden_WhenNotOwner() throws Exception {
        doThrow(new AccessDeniedException("Forbidden")).when(contentsService).delete(anyLong());

        mockMvc.perform(delete("/api/contents/1"))
                .andExpect(status().isForbidden());
    }
}
