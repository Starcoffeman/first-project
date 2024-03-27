package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public List<ItemDto> findItemsByOwner(long userId) {
        List<Item> items = itemRepository.findItemsByOwner(userId);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            ItemDto b = ItemMapper.mapToItemDto(item);
            if (findNextBookingByItemId(item.getId()) != null) {
                b.setNextBooking(findNextBookingByItemId(item.getId()));
                b.setLastBooking(findLastBookingByItemId(item.getId()));
                itemDtos.add(b);
            } else {
                b.setNextBooking(null);
                b.setLastBooking(null);
                itemDtos.add(b);
            }
        }
        return itemDtos;
    }

    @Override
    @Transactional
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

        if (item.getOwner() != userId) {
            throw new ResourceNotFoundException("Отсутствует user под id");
        }

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        item = itemRepository.save(item);
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    @Transactional
    public ItemDto getItemById(long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));
        ItemDto itemDto = ItemMapper.mapToItemDto(item);
        long userId = item.getOwner();
        itemDto.setNextBooking(findNextBookingByItemId(userId));
        itemDto.setLastBooking(findLastBookingByItemId(userId));
        return itemDto;
    }

    @Override
    @Transactional
    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return ItemMapper.mapToItemDto(items);
    }

    @Override
    @Transactional
    public ItemDto saveItem(long userId, ItemDto itemDto) {

        if (userId < 1) {
            throw new ValidationException("Id не может быть отрицательным");
        }

        if (itemDto.getName() == null) {
            throw new ValidationException("Имя не может быть пустым");
        }

        if (itemDto.getDescription() == null) {
            throw new ValidationException("Описание не может быть пустым");
        }

        if (userService.getUserById(userId) == null) {
            throw new ResourceNotFoundException("Отсутствует user под id:");
        }

        Item item = ItemMapper.mapToNewItem(itemDto);
        item.setOwner(userId);
        item = itemRepository.save(item);
        ItemDto a = ItemMapper.mapToItemDto(item);
        a.setNextBooking(findNextBookingByItemId(item.getOwner()));
        a.setLastBooking(findLastBookingByItemId(item.getOwner()));
        a.setComments(commentRepository.findAllByItemId(item.getOwner()));
        return a;
    }

    @Override
    @Transactional
    public List<ItemDto> searchItems(String searchText) {
        if (searchText.equals("")) {
            return new ArrayList<>();
        }
        List<Item> items = itemRepository.findByDescriptionContainingIgnoreCaseAndAvailableIsTrueOrNameContainingIgnoreCaseAndAvailableIsTrue(searchText, searchText);
        return ItemMapper.mapToItemDto(items);
    }

    @Override
    @Transactional
    public CommentDto addComment(long userId, long itemId, String text) {
        boolean hasFutureBooking = bookingRepository.existsByItemIdAndBookerIdAndStatusAndEndBefore(itemId, userId, BookingStatus.APPROVED, LocalDateTime.now());
        if (!hasFutureBooking) {
            throw new ValidationException("User with ID " + userId + " has a future booking for item with ID " + itemId + ". Cannot add comment until the booking is completed.");
        }

        if (text.equals("") || text.isEmpty()) {
            throw new ValidationException("");
        }
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

        UserDto user = userService.getUserById(userId);

        Comment comment = new Comment();
        comment.setText(text);
        comment.setItemId(itemId);
        comment.setAuthorId(userId);
        commentRepository.save(comment);

        List<Comment> comments = item.getComments();
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        item.setComments(comments);

        CommentDto commentDto = CommentMapper.mapToCommentDto(comment);
        commentDto.setAuthorName(user.getName());

        return commentDto;
    }

    private BookingDto findNextBookingByItemId(long itemId) {
        List<Booking> bookings = bookingRepository.findBookingsByItem_Owner(itemId);
        BookingDto nextBookingDto = null;
        LocalDateTime now = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.WAITING && booking.getStart().isAfter(now)) {
                if (nextBookingDto == null || booking.getStart().isBefore(nextBookingDto.getStart())) {
                    nextBookingDto = BookingMapper.mapToBookingDto(booking);
                }
            }
        }
        return nextBookingDto;
    }

    private BookingDto findLastBookingByItemId(long itemId) {
        List<Booking> bookings = bookingRepository.findBookingsByItem_Owner(itemId);
        BookingDto lastBookingDto = null;
        LocalDateTime now = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.APPROVED && booking.getEnd().isBefore(now)) {
                if (lastBookingDto == null || booking.getStart().isAfter(lastBookingDto.getEnd())) {
                    lastBookingDto = BookingMapper.mapToBookingDto(booking);
                }
            }
        }
        return lastBookingDto;
    }
}
