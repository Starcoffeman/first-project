//package ru.practicum.shareit.booking.dto;
//
//import lombok.Builder;
//import lombok.Data;
//import ru.practicum.shareit.booking.model.BookingStatus;
//import ru.practicum.shareit.intf.Create;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.NotNull;
//import java.sql.Timestamp;
//
///**
// * TODO Sprint add-bookings.
// */
//@Data
//@Builder
//
//public class BookingDto {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @NotNull(groups = Create.class, message = " Время не может быть пустым")
//    private Timestamp start;
//
//    @NotNull(groups = Create.class, message = " Время не может быть пустым")
//    private Timestamp end;
//
//    @NotNull(groups =  Create.class,message = "Предмет не может быть пустым")
//    private Long item;
//
//    @NotNull(groups =  Create.class,message = "Бронирующий не может быть пустым")
//    private Long booker;
//
//    @NotNull(groups =  Create.class,message = "Статус не может быть пустым")
//    private BookingStatus status;
//}
