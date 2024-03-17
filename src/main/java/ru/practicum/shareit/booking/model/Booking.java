package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.intf.Create;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private LocalDateTime start;

    @NotNull(groups = Create.class, message = " Время не может быть пустым")
    @Column(name = "finish", nullable = false)
    private LocalDateTime end;

//    @ManyToOne
    @NotNull(groups =  Create.class,message = "Предмет не может быть пустым")
    @JoinColumn(name = "itemId", nullable = false)
    private long item;

//    @ManyToOne
    @NotNull(groups =  Create.class,message = "Бронирующий не может быть пустым")
    @JoinColumn(name = "booker", nullable = false)
    private long booker;

    @NotNull(groups =  Create.class,message = "Статус не может быть пустым")
    @Column(name = "status", nullable = false)
    private BookingStatus status;
}

