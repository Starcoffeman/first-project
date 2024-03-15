//package ru.practicum.shareit.booking;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import ru.practicum.shareit.booking.dto.BookingDto;
//import ru.practicum.shareit.booking.model.Booking;
//import ru.practicum.shareit.intf.Create;
//import ru.practicum.shareit.item.ItemService;
//
///**
// * TODO Sprint add-bookings.
// */
//@RestController
//@RequestMapping(path = "/bookings")
//@RequiredArgsConstructor
//@Slf4j
//public class BookingController {
//
//    private static final String USER_ID = "X-Sharer-User-Id";
//    private final BookingService bookingService;
//
//    @PostMapping
////    @ResponseStatus(HttpStatus.CREATED)
//    public BookingDto saveNewBooking(@RequestHeader(USER_ID) long userId,
//                                     @Validated(Create.class) @RequestBody BookingDto bookingDto) {
//        return bookingService.saveBooking(userId,bookingDto);
//    }
//
//
//}
