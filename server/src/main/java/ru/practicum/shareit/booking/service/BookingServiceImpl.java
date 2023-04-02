package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto addBooking(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException {
        requestValidation(request, userId);
        Booking booking = BookingDtoMapper.addBookingToBooking(request, userId);
        bookingRepository.save(booking);
        log.info("Booking crated, ID: {}", booking.getId());
        return BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
    }

    @Override
    public BookingDto bookingApproval(Integer userId, Integer bookingId, Boolean isApproved) throws NotFoundException, ValidationException {
        Booking booking;
        if (bookingRepository.findById(bookingId).isPresent()) {
            if (!Objects.equals(itemRepository.findById(bookingRepository.findById(bookingId).get().getItemId()).get().getOwnerId(), userId)) {
                throw new NotFoundException("BookingApproval: --NotFoundException--");
            }
            booking = bookingRepository.findById(bookingId).get();
        } else {
            throw new NotFoundException("BookingApproval: --No Booking Found-- bookingId: " + bookingId);
        }
        if (Objects.equals(booking.getStatus(), "APPROVED")) {
            throw new ValidationException("BookingApproval: --ValidationException--");
        }
        if (isApproved) {
            booking.setStatus("APPROVED");
            bookingRepository.save(booking);
        } else {
            booking.setStatus("REJECTED");
            bookingRepository.save(booking);
        }
        return BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
    }

    @Override
    public BookingDto findBookingById(Integer userId, Integer bookingId) throws NotFoundException {
        Booking booking;
        if (bookingRepository.findById(bookingId).isPresent()) {
            booking = bookingRepository.findById(bookingId).get();
        } else {
            throw new NotFoundException("findBookingById: --No Booking Found-- bookingId: " + bookingId);
        }
        if (!Objects.equals(booking.getBooker(), userId) && !Objects.equals(itemRepository.findById(booking.getItemId()).get().getOwnerId(), userId)) {
            throw new NotFoundException("findBookingById: --No User Found or not the Owner--");
        }
        return BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
    }

    @Override
    public List<BookingDto> findAllUserBookings(Integer userId, String state, Integer from, Integer size) throws ValidationException {
        List<BookingDto> bookingDtoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(from / size, size);
        List<Booking> bookings = bookingRepository.findBookingsByBookerOrderByStartDesc(userId, pageable);
        for (Booking booking : bookings) {
            bookingDtoAdding(state, bookingDtoList, booking);
        }
        return bookingDtoList;
    }

    @Override
    public List<BookingDto> findAllOwnerBookings(Integer ownerId, String state, Integer from, Integer size) throws ValidationException {
        List<BookingDto> bookingDtoList = new ArrayList<>();
        List<Integer> itemIds = new ArrayList<>();
        List<Item> items = itemRepository.findItemsByOwnerIdOrderById(ownerId);
        for (Item item : items) {
            itemIds.add(item.getId());
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<Booking> bookings = bookingRepository.findBookingsByItemIdInOrderByStartDesc(itemIds, pageable);
        for (Booking booking : bookings) {
            bookingDtoAdding(state, bookingDtoList, booking);
        }
        return bookingDtoList;
    }

    private void bookingDtoAdding(String state, List<BookingDto> bookingDtoList, Booking booking) throws ValidationException {
        switch (state) {
            case "ALL":
                bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get()));
                break;
            case "CURRENT":
                if (booking.getEnd().isAfter(LocalDateTime.now()) && booking.getStart().isBefore(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get()));
                }
                break;
            case "PAST":
                if (Objects.equals(booking.getStatus(), "APPROVED") && booking.getEnd().isBefore(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get()));
                }
                break;
            case "FUTURE":
                if ((Objects.equals(booking.getStatus(), "APPROVED") || Objects.equals(booking.getStatus(), "WAITING")) && booking.getStart().isAfter(LocalDateTime.now())) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get()));
                }
                break;
            case "WAITING":
                if (Objects.equals(booking.getStatus(), "WAITING")) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get()));
                }
                break;
            case "REJECTED":
                if (Objects.equals(booking.getStatus(), "REJECTED")) {
                    bookingDtoList.add(BookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get()));
                }
                break;
            default:
                throw new ValidationException("Unknown state: " + state);
        }
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    private void requestValidation(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("requestValidation: No User Found--");
        }
        if (itemRepository.findById(request.getItemId()).isPresent()) {
            if (!itemRepository.findById(request.getItemId()).get().getAvailable()) {
                throw new ValidationException("requestValidation: Item isn't available--");
            }
            if (Objects.equals(itemRepository.findById(request.getItemId()).get().getOwnerId(), userId)) {
                throw new NotFoundException("requestValidation: NotFoundException--");
            }
        } else {
            throw new NotFoundException("requestValidation: No Item Found--");
        }
        if ((request.getEnd() == null || request.getStart() == null) || (request.getEnd().isBefore(LocalDateTime.now()) || request.getStart().isBefore(LocalDateTime.now())) || (request.getEnd().isBefore(request.getStart()) || request.getEnd().isEqual(request.getStart()))) {
            throw new ValidationException("requestValidation: ValidationException--");
        }
    }
}