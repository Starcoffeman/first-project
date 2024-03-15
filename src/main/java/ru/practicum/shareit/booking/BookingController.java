package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.intf.Create;
import ru.practicum.shareit.item.ItemService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto saveNewBooking(@RequestBody @Validated(Create.class) BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PutMapping("/{bookingId}")
    public BookingDto updateBooking(@PathVariable long bookingId, @RequestBody @Validated BookingDto bookingDto) {
        return bookingService.updateBooking(bookingId, bookingDto);
    }

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingById(@PathVariable long bookingId) {
        bookingService.deleteBookingById(bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllBookings() {
        return bookingService.getAllBookings();
    }
}


