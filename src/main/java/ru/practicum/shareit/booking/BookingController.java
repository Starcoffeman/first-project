package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.intf.Create;
import ru.practicum.shareit.item.ItemService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private static final String USER_ID = "X-Sharer-User-Id";


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking saveNewBooking(@RequestHeader(USER_ID) long userId,@RequestBody @Validated(Create.class) BookingDto bookingDto) {
        return bookingService.createBooking(userId,bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public Booking updateBookingStatus(@RequestHeader(USER_ID) long userId,
                                       @PathVariable long bookingId,
                                       @RequestParam("approved") boolean approved) {
        return bookingService.setBookingApproval(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingByIdAndBooker(@PathVariable long bookingId, @RequestHeader(USER_ID) long userId) {
        return bookingService.getBookingByIdAndBookerOrOwner(bookingId,userId);
    }



    @GetMapping("/")
    public List<Booking> getBookingsByBooker(@RequestHeader(USER_ID) long userId) {
        return bookingService.findBookingsByBooker_Id(userId);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsByOwner_Id(@RequestHeader(USER_ID) long userId,
                                               @RequestParam(value = "state", required = false) String state
                                                ) {

        if (!bookingService.existsBookingByBooker_IdOrItem_Owner(userId, userId)) {
            throw new ResourceNotFoundException("No bookings found for user with ID: " + userId);
        }

        if (state!=null){
            if(state.equals("ALL")){
                return bookingService.findBookingsByItem_Owner(userId);
            }
            if(state.equals("FUTURE")){
                return bookingService.findBookingsByItem_Owner(userId);
            }

            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        } else {
            return bookingService.findBookingsByBooker_IdOrItem_Owner(userId,userId);
        }
    }

//    @GetMapping("/owner")
//    public List<Booking> getBookingsByBooker_Id(@RequestHeader(USER_ID) long userId,
//                                            @RequestParam(value = "state", required = false) String state) {
//        if (state != null) {
//            BookingStatus status;
//            try {
//                status = BookingStatus.valueOf(state.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                throw new ValidationException("Unknown state: " + state);
//            }
//            return bookingService.findBookingsByStatusAndBooker_Id(status,userId);
//        } else {
//            return bookingService.findBookingsByBookerId(userId);
//        }
//    }

    @GetMapping
    public List<Booking> getBookingsByBooker_Id(@RequestHeader(USER_ID) long userId,
                                            @RequestParam(value = "state", required = false) String state) {

        if (state!=null){
            if(state.equals("ALL")){
                return bookingService.findBookingsByBooker_Id(userId);
            }
            if(state.equals("FUTURE")){
                return bookingService.findBookingsByBooker_Id(userId);
            }

            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        } else {
        return bookingService.findBookingsByBooker_IdOrItem_Owner(userId,userId);
        }
    }


}


