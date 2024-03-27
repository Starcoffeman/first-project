package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.LastBooking;
import ru.practicum.shareit.booking.model.NextBooking;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
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

    List<ItemDto> findItemsByOwner(long userId);

    CommentDto addComment(long userId, long itemId, String text);
    List<ItemDto> searchItems(String searchText);

    BookingDto findNextBookingByItemId(long itemId);

    BookingDto findLastBookingByItemId(long itemId);


}
