package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.List;


public interface BookingService {


    Booking setBookingApproval(long userId, long bookingId, boolean approved);

    Booking createBooking(long userId, BookingDto bookingDto);

    Booking getBookingByIdAndBookerOrOwner(long bookingId, long userId);

    Booking getBookingById(long bookingId);

    List<Booking> findBookingsByBooker_Id(long userId);


    boolean existsBookingByBooker_IdOrItem_Owner(long bookerId, long ownerId);

    List<Booking> findBookingsByItem_Owner(long userId);

    Booking findBookingByItem_Owner(long userId);

    List<Booking> findBookingsByStatusAndBooker_Id(BookingStatus status, long userId);

    List<Booking> findBookingsByBooker_IdOrItem_Owner(long bookerId,long ownerId);


}
