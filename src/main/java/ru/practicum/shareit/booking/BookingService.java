package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface BookingService {

    List<BookingDto> getAllBookings();

    void deleteBookingById(long bookingId);

    BookingDto getBookingById(long bookingId);

    Booking createBooking(long userId,BookingDto bookingDto);

    BookingDto updateBooking(long bookingId, BookingDto bookingDto);

}
