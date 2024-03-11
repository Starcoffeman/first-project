//package ru.practicum.shareit.booking;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.practicum.shareit.booking.dto.BookingDto;
//import ru.practicum.shareit.booking.model.Booking;
//import ru.practicum.shareit.item.ItemMapper;
//import ru.practicum.shareit.item.ItemRepository;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.user.UserRepository;
//
//import java.util.List;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class BookingServiceImpl implements BookingService{
//
//    private final BookingRepository repository;
//
//    @Override
//    public List<BookingDto> getAllBookings() {
//        List<Booking> bookings =repository.findAll();
//        return BookingMapper.mapToBookingDto(bookings);
//    }
//
//    @Override
//    @Transactional
//    public BookingDto saveBooking(long userId,BookingDto bookingDto) {
//        Booking booking = repository.save(BookingMapper.mapToNewBooking(bookingDto));
//        booking.setBooker(userId);
//        return BookingMapper.mapToBookingDto(booking);
//    }
//
//    @Override
//    public Item update(long userId, long itemId, ItemDto itemDto) {
//        return null;
//    }
//
//    @Override
//    public Item getItemById(long userId) {
//        return null;
//    }
//
//    @Override
//    public List<Item> searchItems(String searchText) {
//        return null;
//    }
//}
