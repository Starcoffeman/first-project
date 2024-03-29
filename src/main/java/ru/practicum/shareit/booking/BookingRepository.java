package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByBooker_Id(long userId);

    List<Booking> findBookingsByItem_Owner(long ownerId);

    List<Booking> findBookingsByItemId(long itemId);

    boolean existsBookingsByBooker_IdOrItem_Owner(long bookerId, long ownerId);

    List<Booking> findBookingsByBooker_IdOrItem_Owner(long bookerId, long ownerId);

    boolean existsByItemIdAndBookerIdAndStatusAndEndBefore(Long itemId, Long bookerId, BookingStatus status, LocalDateTime localDateTime);

    Booking findFirstBookingByItemIdAndStatusAndStartIsBefore(long itemId, BookingStatus bookingStatus, LocalDateTime now, Sort start);

    Booking findFirstBookingByItemIdAndStatusAndStartIsAfter(long itemId, BookingStatus bookingStatus, LocalDateTime now, Sort start);
}








