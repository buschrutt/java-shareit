package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.practicum.shareit.request.dto.RequestDto;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    private final RequestDto requestDto0 = RequestDto.builder()
            .id(2)
            .description("description2")
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

    @SneakyThrows
    @Test
    void findUserRequestsTest() {
        List<RequestDto> requestDtoList = new ArrayList<>();
        requestDtoList.add(requestDto);
        when(requestController.findUserRequests(anyInt())).thenReturn(requestDtoList);
        mvc.perform(get("/requests")
                        .content(mapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].description", is(requestDto.getDescription())));
    }

    @SneakyThrows
    @Test
    void findAllRequestsTest() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "0");
        requestParams.add("size", "10");
        List<RequestDto> requestDtoList = new ArrayList<>();
        requestDtoList.add(requestDto);
        requestDtoList.add(requestDto0);
        when(requestController.findAllRequests(anyInt(), anyInt(), anyInt())).thenReturn(requestDtoList);
        mvc.perform(get("/requests/all")
                        .params(requestParams)
                        .content(mapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].description", is(requestDto.getDescription())))
                .andExpect(jsonPath("$[1].id", is(requestDto0.getId()), Integer.class))
                .andExpect(jsonPath("$[1].description", is(requestDto0.getDescription())));
    }

    @SneakyThrows
    @Test
    void findRequestByIdTest() {
        Integer requestId = 1;
        when(requestController.findRequestById(any(), any())).thenReturn(requestDto);
        mvc.perform(get("/requests/{requestId}", requestId)
                        .content(mapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(requestDto.getDescription())));
    }

}
