package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public class BookingMapper {

    public static BookingDto mapToBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setBooker(booking.getBooker().getId());
        bookingDto.setItemId(booking.getItem().getId());
        bookingDto.setStatus(booking.getStatus());

        return bookingDto;
    }

    public static List<BookingDto> mapToBookingDto(Iterable<Booking> bookings) {
        List<BookingDto> result = new ArrayList<>();

        for (Booking booking : bookings) {
            result.add(mapToBookingDto(booking));
        }

        return result;
    }

}
