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
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto addBooking(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException {
        requestValidation(request, userId);
        Booking booking = BookingDtoMapper.addBookingToBooking(request, userId);
        bookingRepository.save(booking);
        return BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
    }

    @Override
    public BookingDto BookingApproval(Integer userId, Integer bookingId, Boolean isApproved) throws NotFoundException {
        Booking booking;
        if (bookingRepository.findById(bookingId).isPresent()) {
            booking = bookingRepository.findById(bookingId).get();
        } else {
            throw new NotFoundException("BookingApproval: No Booking Found--");
        }
        if (isApproved) {
            booking.setStatus("APPROVED");
            bookingRepository.save(booking);
        } else {
            booking.setStatus("REJECTED");
            bookingRepository.save(booking);
        }
        return BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
    }

    @Override
    public BookingDto findBookingById(Integer userId, Integer bookingId) throws NotFoundException, ValidationException {
        Booking booking;
        if (bookingRepository.findById(bookingId).isPresent()) {
            booking = bookingRepository.findById(bookingId).get();
        } else {
            throw new NotFoundException("findBookingById: No Booking Found--");
        }
        if (!Objects.equals(booking.getBooker(), userId) && !Objects.equals(itemRepository.findById(booking.getItemId()).get().getOwnerId(), userId)) {
            throw new NotFoundException("findBookingById: User isn't authorized--");
        }
        return BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
    }

    @Override
    public List<BookingDto> findAllUserBookings(Integer userId, String state, Integer from, Integer size) throws NotFoundException, ValidationException {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("findAllUserBookings: No User Found--");
        }
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookingRepository.userBookingsSorted(userId)) {
            if (Objects.equals(state, "ALL")) {
                bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
            } else if (Objects.equals(state, "CURRENT")) {
                if (Objects.equals(booking.getStatus(), "APPROVED") && booking.getEnd().isAfter(LocalDateTime.now()) && booking.getStart().isBefore(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "PAST")) {
                if (Objects.equals(booking.getStatus(), "APPROVED") && booking.getEnd().isBefore(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "FUTURE")) {
                if ((Objects.equals(booking.getStatus(), "APPROVED") || Objects.equals(booking.getStatus(), "WAITING")) && booking.getStart().isAfter(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "WAITING")) {
                if (Objects.equals(booking.getStatus(), "WAITING")) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "REJECTED")) {
                if (Objects.equals(booking.getStatus(), "REJECTED")) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else {
                throw new ValidationException("Unknown state: " + state);
            }
        }
        return bookingDtoList;
    }

    @Override
    public List<BookingDto> findAllOwnerBookings(Integer ownerId, String state, Integer from, Integer size) throws NotFoundException, ValidationException {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("findAllUserBookings: No User Found--");
        }
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookingRepository.ownerBookingsSorted(ownerId)) {
            if (Objects.equals(state, "ALL")) {
                bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
            } else if (Objects.equals(state, "CURRENT")) {
                if (Objects.equals(booking.getStatus(), "APPROVED") && booking.getEnd().isAfter(LocalDateTime.now()) && booking.getStart().isBefore(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "PAST")) {
                if (Objects.equals(booking.getStatus(), "APPROVED") && booking.getEnd().isBefore(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "FUTURE")) {
                if ((Objects.equals(booking.getStatus(), "APPROVED") || Objects.equals(booking.getStatus(), "WAITING")) && booking.getStart().isAfter(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "WAITING")) {
                if (Objects.equals(booking.getStatus(), "WAITING")) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else if (Objects.equals(state, "REJECTED")) {
                if (Objects.equals(booking.getStatus(), "REJECTED")) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository));
                }
            } else {
                throw new ValidationException("Unknown state: " + state);
            }
        }


        return bookingDtoList;
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
