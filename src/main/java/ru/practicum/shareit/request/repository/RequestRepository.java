package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.Request;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer>  {

    List<Request> findRequestsByRequesterIdOrderByCreatedDesc(Integer requesterId);

    List<Request> findRequestsByRequesterIdIsNotOrderByCreatedDesc(Integer userId, Pageable pageable);
}
