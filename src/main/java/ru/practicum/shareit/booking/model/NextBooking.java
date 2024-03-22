package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NextBooking {

    private long id;
    private long bookerId;

}
