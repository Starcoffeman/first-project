package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.intf.Create;
import ru.practicum.shareit.booking.model.LastBooking;
import ru.practicum.shareit.booking.model.NextBooking;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items", schema = "public")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(groups = Create.class, message = "Описание не может быть пустым")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(groups = Create.class, message = "Описание не может быть пустым")
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "owner", nullable = false)
    private long owner;

    @Column(name = "request", nullable = false)
    private long request;

}
