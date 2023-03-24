package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoMapper;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto addBooking(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException {
        requestValidation(request, userId);
        Booking booking = BookingDtoMapper.toBooking(request);
        booking.setStatus("WAITING");
        booking.setBooker(userId);
        booking.setItemId(request.getItemId());
        bookingRepository.save(booking);
        BookingDto bookingDto = BookingDtoMapper.toBookingDto(booking);
        bookingDto.setBooker(userRepository.findById(userId).get());
        bookingDto.setItem(itemRepository.findById(request.getItemId()).get());
        return bookingDto;
    }

    @Override
    public BookingDto BookingApproval(Integer userId, Integer bookingId, Boolean isApproved) {
        return null;
    }

    @Override
    public BookingDto findBookingById(Integer userId, Integer bookingId) {
        return null;
    }

    @Override
    public List<BookingDto> findAllUserBookings(Integer userId, String state, Integer from, Integer size) {
        return null;
    }

    @Override
    public List<BookingDto> findAllUserItemBookings(Integer userId, String state, Integer from, Integer size) {
        return null;
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    private void requestValidation(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("requestValidation: No User Found--");
        }
        if (itemRepository.findById(request.getItemId()).isPresent()) {
            if(!itemRepository.findById(request.getItemId()).get().getAvailable()) {
                throw new ValidationException("requestValidation: Item isn't available--");
            }
        } else {
            throw new NotFoundException("requestValidation: No Item Found--");
        }
        if (request.getEnd() == null || request.getStart() == null) {
            throw new ValidationException("requestValidation: Null--");
        }
        if (request.getEnd().isBefore(LocalDateTime.now()) || request.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("requestValidation: End or Start in a Past--");
        }
        if (request.getEnd().isBefore(request.getStart()) || request.getEnd().isEqual(request.getStart())) {
            throw new ValidationException("requestValidation: End is Before or the Same as Start--");
        }
    }
}
