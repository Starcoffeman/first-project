package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public interface BookingService {

//    List<BookingDto> getAllBookings();
//
//    void deleteBookingById(long bookingId);

    //    List<Booking> findBookingById();
    Booking setBookingApproval(long userId,long bookingId, boolean approved);

    Booking getBookingByIdAndBooker(long bookingId, long bookerId);
    Booking createBooking(long userId, BookingDto bookingDto);

//    BookingDto updateBooking(long bookingId, BookingDto bookingDto);

}
