package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.intf.Create;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings", schema = "public")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(groups = Create.class, message = " Время не может быть пустым")
    @Column(name = "start", nullable = false)
    private Timestamp start;

    @NotNull(groups = Create.class, message = " Время не может быть пустым")
    @Column(name = "finish", nullable = false)
    private Timestamp finish;

    @NotNull(groups =  Create.class,message = "Предмет не может быть пустым")
    @Column(name = "item", nullable = false)
    private Long item;

    @NotNull(groups =  Create.class,message = "Бронирующий не может быть пустым")
    @Column(name = "booker", nullable = false)
    private Long booker;

    @NotNull(groups =  Create.class,message = "Статус не может быть пустым")
    @Column(name = "status", nullable = false)
    private BookingStatus status;
}

