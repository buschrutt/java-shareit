package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.RequestDto;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = RequestController.class)
public class RequestControllerTests {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    RequestController requestController;
    @Autowired
    private MockMvc mvc;
    final String header = "X-Sharer-User-Id";
    private final RequestDto requestDto = RequestDto.builder()
            .id(1)
            .description("description")
            .created(LocalDateTime.now())
            .build();

    @SneakyThrows
    @Test
    void addRequestTest() {
        when(requestController.addRequest(any(), any())).thenReturn(requestDto);
        mvc.perform(post("/requests")
                .content(mapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(requestDto.getDescription())));
    }

}
