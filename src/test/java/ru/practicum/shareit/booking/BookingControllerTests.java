package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTests {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    BookingController bookingController;
    @Autowired
    private MockMvc mvc;
    final String header = "X-Sharer-User-Id";
    private final BookingDto bookingDto = BookingDto.builder()
            .id(1)
            .start(LocalDateTime.now())
            .end(LocalDateTime.now())
            .item(Item.builder()
                    .id(1)
                    .name("ItemName")
                    .description("description")
                    .available(true)
                    .requestId(1)
                    .ownerId(2)
                    .build())
            .booker(User.builder()
                    .id(1)
                    .name("UserName1")
                    .email("user1@mail.ru")
                    .build())
            .status("APPROVED")
            .build();

    @SneakyThrows
    @Test
    void addBookingControllerTest() {
        when(bookingController.addBooking(any(), any())).thenReturn(bookingDto);
        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())))
                .andExpect(jsonPath("$.item.description", is(bookingDto.getItem().getDescription())))
                .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$.booker.email", is(bookingDto.getBooker().getEmail())));
    }

    @SneakyThrows
    @Test
    void bookingApprovalControllerTest() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("approved", "true");
        Integer bookingId = 1;
        when(bookingController.bookingApproval(any(), any(), any())).thenReturn(bookingDto);
        mvc.perform(patch("/bookings/{bookingId}", bookingId)
                        .params(requestParams)
                        .content(mapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())))
                .andExpect(jsonPath("$.item.description", is(bookingDto.getItem().getDescription())))
                .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$.booker.email", is(bookingDto.getBooker().getEmail())));
    }

    @SneakyThrows
    @Test
    void findBookingByIdControllerTest() {
        Integer bookingId = 1;
        when(bookingController.findBookingById(any(), any())).thenReturn(bookingDto);
        mvc.perform(get("/bookings/{bookingId}", bookingId)
                        .content(mapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$.item.name", is(bookingDto.getItem().getName())))
                .andExpect(jsonPath("$.item.description", is(bookingDto.getItem().getDescription())))
                .andExpect(jsonPath("$.booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$.booker.email", is(bookingDto.getBooker().getEmail())));
    }

    @SneakyThrows
    @Test
    void findAllUserBookingsControllerTest() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("state", "ALL");
        requestParams.add("from", "0");
        requestParams.add("size", "10");
        List<BookingDto> bookingDtoList = new ArrayList<>();
        bookingDtoList.add(bookingDto);
        Integer bookingId = 1;
        when(bookingController.findAllUserBookings(any(), any(), any(), any())).thenReturn(bookingDtoList);
        mvc.perform(get("/bookings", bookingId)
                        .params(requestParams)
                        .content(mapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].item.name", is(bookingDto.getItem().getName())))
                .andExpect(jsonPath("$[0].item.description", is(bookingDto.getItem().getDescription())))
                .andExpect(jsonPath("$[0].booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$[0].booker.email", is(bookingDto.getBooker().getEmail())));
    }

    @SneakyThrows
    @Test
    void findAllOwnerBookingsControllerTest() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("state", "ALL");
        requestParams.add("from", "0");
        requestParams.add("size", "10");
        List<BookingDto> bookingDtoList = new ArrayList<>();
        bookingDtoList.add(bookingDto);
        when(bookingController.findAllOwnerBookings(any(), any(), any(), any())).thenReturn(bookingDtoList);
        mvc.perform(get("/bookings/owner")
                        .params(requestParams)
                        .content(mapper.writeValueAsString(bookingDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].item.name", is(bookingDto.getItem().getName())))
                .andExpect(jsonPath("$[0].item.description", is(bookingDto.getItem().getDescription())))
                .andExpect(jsonPath("$[0].booker.name", is(bookingDto.getBooker().getName())))
                .andExpect(jsonPath("$[0].booker.email", is(bookingDto.getBooker().getEmail())));
    }

}
