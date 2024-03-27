package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByBooker_Id(long userId);

    List<Booking> findBookingsByItem_Owner(long userId);

    boolean existsBookingsByBooker_IdOrItem_Owner(long bookerId, long ownerId);

    List<Booking> findBookingsByBooker_IdOrItem_Owner(long bookerId, long ownerId);

    boolean existsByItemIdAndBookerIdAndStatusAndEndBefore(Long itemId, Long bookerId, BookingStatus status, LocalDateTime localDateTime);
}








