package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {


    Booking setBookingApproval(long userId, long bookingId, boolean approved);

    Booking createBooking(long userId, BookingDto bookingDto);

    Booking getBookingByIdAndBookerOrOwner( long bookingId, long userId);

    Booking getBookingById(long bookingId);

}
