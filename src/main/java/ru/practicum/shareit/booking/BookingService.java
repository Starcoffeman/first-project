package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.LastBooking;
import ru.practicum.shareit.booking.model.NextBooking;

import java.util.List;
import java.util.Optional;


public interface BookingService {


    Booking setBookingApproval(long userId, long bookingId, boolean approved);

    Booking createBooking(long userId, BookingDto bookingDto);

    Booking getBookingByIdAndBookerOrOwner(long bookingId, long userId);

    Booking getBookingById(long bookingId);

    List<Booking> findBookingsByBooker_Id(long userId);


    boolean existsBookingByBooker_IdOrItem_Owner(long bookerId, long ownerId);

    List<Booking> findBookingsByItem_Owner(long userId);

    List<Booking> findBookingsByBooker_IdOrItem_Owner(long bookerId,long ownerId);

    List<Booking> findBookingsByBooker_IdAndStatus_Waiting(long userId);

    List<Booking> findBookingsByItem_OwnerAndStatus_Waiting(long userId);

    List<Booking> findBookingsByItem_OwnerAndStatus_Rejected(long userId);
    List<Booking> findBookingsByBooker_IdAndStatus_Rejected(long userId);

    List<Booking> findPastBookingsByOwner_Id(long userId);
    List<Booking> findPastBookingsByBookerId(long userId);

    List<Booking> findCurrentBookingsByBookerId(long userId);

    List<Booking> findCurrentBookingsByOwner_Id(long userId);

    NextBooking findNextBookingByItemId(long itemId);

}
