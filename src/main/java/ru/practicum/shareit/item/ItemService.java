package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.LastBooking;
import ru.practicum.shareit.booking.model.NextBooking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    ItemDto getItemById(long userId);

    List<ItemDto> getAllItems();

    ItemDto saveItem(long userId, ItemDto itemDto);

    //    void deleteItemById(long userId);
    List<ItemDto> findItemsByOwner(long userId);

    List<ItemDto> searchItems(String searchText);
//    LastBooking findLastBookingByItemId(long itemId);
//    User findLastBookerByItemId(long itemId);
//    User findNextBookerByItemId(long itemId);




}
