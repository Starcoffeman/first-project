package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository  extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b JOIN FETCH b.item JOIN FETCH b.booker")
    List<Booking> findBookingById();

    Booking findByIdAndBookerId(long bookingId, long bookerId);

}
