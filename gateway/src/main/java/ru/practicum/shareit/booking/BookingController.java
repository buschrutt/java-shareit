package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.error.ValidationException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.Constants.HEADER;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	// findAllUserBookings
	@GetMapping
	public ResponseEntity<Object> getBookings(@RequestHeader(HEADER) long userId,
			@RequestParam(name = "state", defaultValue = "all") String stateParam,
			@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
			@Positive @RequestParam(name = "size", defaultValue = "10") Integer size) throws ValidationException {
		BookingState state = BookingState.from(stateParam)
				.orElseThrow(() -> new ValidationException("Unknown state: " + stateParam));
		log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
		return bookingClient.getBookings(userId, state, from, size);
	}

	// addBooking
	@PostMapping
	public ResponseEntity<Object> bookItem(@RequestHeader(HEADER) long userId,
			@RequestBody @Valid BookItemRequestDto requestDto) {
		log.info("Creating booking {}, userId={}", requestDto, userId);
		return bookingClient.bookItem(userId, requestDto);
	}

	// findBookingById
	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBooking(@RequestHeader(HEADER) long userId,
			@PathVariable Long bookingId) {
		log.info("Get booking {}, userId={}", bookingId, userId);
		return bookingClient.getBooking(userId, bookingId);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> bookingApproval(@RequestHeader(value = HEADER) Long userId,
												  @PathVariable Integer bookingId,
												  @RequestParam Boolean approved) {
		return bookingClient.bookingApproval(userId, bookingId, approved);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> findAllOwnerBookings(@RequestHeader(value = HEADER, required = false) long userId,
													   @RequestParam(name = "state", defaultValue = "all") String stateParam,
													   @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
													   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) throws ValidationException {

		BookingState state = BookingState.from(stateParam)
				.orElseThrow(() -> new ValidationException("Unknown state: " + stateParam));
		return bookingClient.findAllOwnerBookings(userId, state, from, size);
	}

}
