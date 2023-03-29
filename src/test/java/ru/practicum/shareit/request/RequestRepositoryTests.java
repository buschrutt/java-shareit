package ru.practicum.shareit.request;

import lombok.experimental.PackagePrivate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@PackagePrivate
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RequestRepositoryTests {

    @Autowired
    RequestRepository requestRepository;

    @BeforeEach
    void beforeEach() {
        requestRepository.save(Request.builder()
                .created(LocalDateTime.now())
                .description("description1")
                .requesterId(1)
                .build());
        requestRepository.save(Request.builder()
                .created(LocalDateTime.now())
                .description("description2")
                .requesterId(2)
                .build());
        requestRepository.save(Request.builder()
                .created(LocalDateTime.now())
                .description("description3")
                .requesterId(1)
                .build());
    }

    @AfterEach
    void afterEach() {
        requestRepository.deleteAll();
    }

    @Test
    void requestRepositoryTest() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Request> requestDtoList = requestRepository.findRequestsByRequesterIdIsNotOrderByCreatedDesc(2, pageable);
        assertEquals(requestDtoList.size(), 2);
        assertTrue(requestDtoList.get(0).getCreated().isAfter(requestDtoList.get(1).getCreated()));
        requestDtoList = requestRepository.findRequestsByRequesterIdOrderByCreatedDesc(1);
        assertEquals(requestDtoList.size(), 2);
        assertTrue(requestDtoList.get(0).getCreated().isAfter(requestDtoList.get(1).getCreated()));
    }


}
