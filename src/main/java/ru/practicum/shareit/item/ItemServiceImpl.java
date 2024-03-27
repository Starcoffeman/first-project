package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingService;
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
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public List<ItemDto> findItemsByOwner(long userId) {
        List<Item> items = itemRepository.findItemsByOwner(userId);
        List<ItemDto> a = new ArrayList<>();
        for (Item item : items) {
            ItemDto b = ItemMapper.mapToItemDto(item);
            if (findNextBookingByItemId(item.getId()) != null) {
                b.setNextBooking(findNextBookingByItemId(item.getId()));
                b.setLastBooking(findLastBookingByItemId(item.getId()));
                a.add(b);
            } else {
                b.setNextBooking(null);
                b.setLastBooking(null);
                a.add(b);
            }
        }
        return a;
    }

    @Override
    @Transactional
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

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
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

        ItemDto itemDto = ItemMapper.mapToItemDto(item);

        long userId = item.getOwner();

        itemDto.setNextBooking(findNextBookingByItemId(userId));
        itemDto.setLastBooking(findNextBookingByItemId(userId));

        return itemDto;
    }

//    public BookingDto findNextBookingByItemIdAndUserId(long itemId, long userId) {
//        List<Booking> bookings = bookingRepository.findBookingsByBooker_IdOrItem_Owner(itemId, userId);
//        BookingDto nextBookingDto = null;
//
//        for (Booking booking : bookings) {
//            if (booking.getStatus() == BookingStatus.WAITING) {
//                if (nextBookingDto == null || booking.getStart().isBefore(nextBookingDto.getStart())) {
//                    nextBookingDto = BookingMapper.mapToBookingDto(booking);
//                }
//            }
//        }
//
//        return nextBookingDto;
//    }
//
//    public BookingDto findLastBookingByItemIdAndUserId(long itemId, long userId) {
//        List<Booking> bookings = bookingRepository.findBookingsByBooker_IdOrItem_Owner(itemId, userId);
//        BookingDto lastBookingDto = null;
//
//        for (Booking booking : bookings) {
//            if (lastBookingDto == null || booking.getStart().isAfter(lastBookingDto.getStart())) {
//                lastBookingDto = BookingMapper.mapToBookingDto(booking);
//            }
//        }
//
//        return lastBookingDto;
//    }


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

    public BookingDto findNextBookingByItemId(long itemId) {
        List<Booking> bookings = bookingRepository.findBookingsByItem_Owner(itemId);
        BookingDto nextBookingDto = null;

        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.WAITING) {
                if (nextBookingDto == null || booking.getStart().isBefore(nextBookingDto.getStart())) {
                    nextBookingDto = BookingMapper.mapToBookingDto(booking);
                }
            }
        }

        return nextBookingDto;
    }

    public BookingDto findLastBookingByItemId(long itemId) {
        List<Booking> bookings = bookingRepository.findBookingsByItem_Owner(itemId);
        BookingDto lastBookingDto = null;

        for (Booking booking : bookings) {
            if (lastBookingDto == null || booking.getStart().isAfter(lastBookingDto.getStart())) {
                lastBookingDto = BookingMapper.mapToBookingDto(booking);
            }
        }

        return lastBookingDto;
    }

    @Override
    @Transactional
    public CommentDto addComment(long userId, long itemId, String text) {

        boolean hasApprovedBooking = bookingRepository.existsByItemIdAndBookerIdAndStatus(itemId, userId, BookingStatus.APPROVED);
        if (!hasApprovedBooking) {
            throw new ValidationException("User with ID " + userId + " has not booked item with ID " + itemId + " or the booking is not approved");
        }

        if (text == null || text.trim().isEmpty()|| text.equals("")) {
            throw new ValidationException("Comment text cannot be empty");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));

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

}
