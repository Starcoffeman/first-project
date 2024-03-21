package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {


    List<Booking> findBookingsByBooker_Id(long userId);

    List<Booking> findBookingsByItem_Owner(long userId);

    Booking findBookingByItem_Owner(long userId);

    boolean existsBookingsByBooker_IdOrItem_Owner(long bookerId, long ownerId);
    List<Booking> findBookingsByBooker_IdOrItem_Owner(long bookerId,long ownerId);

    List<Booking> findBookingsByStatusAndBooker_Id(BookingStatus status, long userId);

}
