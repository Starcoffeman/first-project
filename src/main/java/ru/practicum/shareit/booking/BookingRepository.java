package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.NextBooking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<Booking, Long> {


    List<Booking> findBookingsByBooker_Id(long userId);

    List<Booking> findBookingsByItem_Owner(long userId);

    List<Booking> findByItem_OwnerOrBooker_Id(long itemId, long userId);


    boolean existsByItem_IdAndBooker_Id(long itemId,long userId);
//    Booking findBookingByItem_Owner(long userId);

    boolean existsBookingsByBooker_IdOrItem_Owner(long bookerId, long ownerId);
    List<Booking> findBookingsByBooker_IdOrItem_Owner(long bookerId,long ownerId);

    // Найти последнее бронирование для предмета по его идентификатору и отсортировать по времени окончания в порядке убывания
//    Optional<Booking> findTopByItemIdOrderByEndDesc(Long itemId);

    // Найти следующее бронирование для предмета по его идентификатору, начинающееся после указанного времени и отсортировать по времени начала в порядке возрастания
//    Optional<Booking> findTopByItemIdAndStartAfterOrderByStart(Long itemId, LocalDateTime start);
//
//    Optional<Booking> findTopByItemIdOrderByEndDesc(long itemId);
//
//    Optional<Booking> findTopByItemIdAndStartAfterOrderByStart(long itemId, LocalDateTime start);
    List<Booking> findByItemIdAndStatusOrderByStartAsc(long itemId, BookingStatus status);

    boolean existsByItemIdAndBookerIdAndStatus(long itemId, long bookerId,BookingStatus bookingStatus);



}







