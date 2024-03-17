package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final Timestamp ts = Timestamp.from(Instant.now());

    @Override
    @Transactional
    public Booking createBooking(long userId, BookingDto bookingDto) {

        if (bookingDto.getStart() == null) {
            throw new ValidationException("Booking start time cannot be null");
        }

        if (bookingDto.getEnd() == null) {
            throw new ValidationException("Booking end time cannot be null");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        if (!itemRepository.existsById(bookingDto.getItemId())) {
            throw new ResourceNotFoundException("Item not found with ID: " + userId);
        }

        if (itemRepository.getById(bookingDto.getItemId()).getAvailable() == false) {
            throw new ValidationException("adsad");
        }
        // Проверка на то, что время начала бронирования не может быть в прошлом

        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Booking start time cannot be in the past");
        }

        // Проверка на то, что время окончания бронирования не может быть раньше времени начала
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new ValidationException("Booking end time cannot be before booking start time");
        }

        // Проверка на то, что время начала бронирования не может быть равным времени окончания
        if (bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new ValidationException("Booking start time cannot be equal to booking end time");
        }

        // Create a new Booking object
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(bookingDto.getItemId());
        booking.setBooker(userId);
        booking.setStatus(BookingStatus.WAITING);

        // Save the new booking to the database
        booking = repository.save(booking);

        // Map the saved booking entity back to DTO and return it
//        return BookingMapper.mapToBookingDto(booking);
        return repository.save(booking);
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

        if (bookingDto.getEnd() != null) {
            booking.setEnd(bookingDto.getEnd());
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
