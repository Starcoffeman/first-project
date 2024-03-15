package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.item.ItemMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final  Timestamp ts = Timestamp.from(Instant.now());

    @Override
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = BookingMapper.mapToNewBooking(bookingDto);
        return BookingMapper.mapToBookingDto(repository.save(booking));
    }

    @Override
    public BookingDto getBookingById(long bookingId) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    @Transactional
    public BookingDto updateBooking(long bookingId, BookingDto bookingDto) {
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));


        if (bookingDto.getStart() != null) {
            booking.setStart(bookingDto.getStart());
        }

        if (bookingDto.getFinish() != null) {
            booking.setFinish(bookingDto.getFinish());
        }

        if (bookingDto.getItem() != null) {
            booking.setItem(bookingDto.getItem());
        }

        if (bookingDto.getBooker() != null) {
            booking.setBooker(bookingDto.getBooker());
        }

        if (bookingDto.getStatus() != null) {
            booking.setStatus(bookingDto.getStatus());
        }

        booking = repository.save(booking); // Save the updated item


        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    @Transactional
    public void deleteBookingById(long bookingId) {
        if (bookingId < 1) {
            throw new IllegalArgumentException("Booking ID must be greater than 0");
        }
        repository.deleteById(bookingId);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = repository.findAll();
        return BookingMapper.mapToBookingDto(bookings);
    }
}
