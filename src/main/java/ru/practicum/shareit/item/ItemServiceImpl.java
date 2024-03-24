package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.model.LastBooking;
import ru.practicum.shareit.booking.model.NextBooking;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingService bookingService;

    @Override
    @Transactional
    public List<ItemDto> findItemsByOwner(long userId) {
        List<Item> items = itemRepository.findItemsByOwner(userId);
        return ItemMapper.mapToItemDto(items);
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + itemId));

        return ItemMapper.mapToItemDto(item);
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
//        a.setLastBooking(findLastBookingByItemId(item.getId()));
//        a.setNextBooking(findNextBookingByItemId(item.getId()));
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


    public NextBooking findNextBookingByItemId(long itemId) {
        List<Booking> bookings = bookingService.findBookingsByItem_Owner(itemId);
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.WAITING) {
                result.add(booking);
            }
        }

        // Сортируем список бронирований по времени начала в обратном порядке
        result.sort(Comparator.comparing(Booking::getStart).reversed());

        // Проверяем, есть ли бронирования в списке
        if (!result.isEmpty()) {
            // Создаем объект NextBooking с идентификатором бронирующего пользователя
            // и идентификатором самого бронирования
            NextBooking nextBooking = new NextBooking(result.get(0).getId(), result.get(0).getBooker().getId());
            return nextBooking;
        } else {
            // Если список бронирований пуст, возвращаем null или выбрасываем исключение, в зависимости от вашего дизайна
            return null;
        }
    }



    public LastBooking findLastBookingByItemId(long itemId) {
        return null;
    }


//    public void updateLastBooking(Long itemId, Long bookingId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));
//        item.setLastBooking(LastBooking.builder()
//                .id(booking.getId())
//                .bookerId(booking.getBooker().getId())
//                .build());
//        itemRepository.save(item);
//    }
//
//    public void updateNextBooking(Long itemId, Long bookingId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));
//        item.setNextBooking(NextBooking.builder()
//                .id(booking.getId())
//                .bookerId(booking.getBooker().getId())
//                .build());
//        itemRepository.save(item);
//    }
//
//    public Optional<Booking> findLastBooking(Long itemId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
//        LastBooking lastBooking = item.getLastBooking();
//        if (lastBooking != null) {
//            return bookingRepository.findById(lastBooking.getId());
//        }
//        return Optional.empty();
//    }
//
//    public Optional<Booking> findNextBooking(Long itemId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
//        NextBooking nextBooking = item.getNextBooking();
//        if (nextBooking != null) {
//            return bookingRepository.findById(nextBooking.getId());
//        }
//        return Optional.empty();
//    }
//
//    public void bookItem(Long itemId, Long bookingId) {
//        updateLastBooking(itemId, bookingId);
//
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
//        item.setAvailable(false);
//        itemRepository.save(item);
//    }
//
//    public void unbookItem(Long itemId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
//        item.setAvailable(true);
//        itemRepository.save(item);
//    }
//
//    public void processNextBooking(Long itemId) {
//        Optional<Booking> optionalNextBooking = findNextBooking(itemId);
//        if (optionalNextBooking.isPresent()) {
//            Booking nextBooking = optionalNextBooking.get();
//            if (nextBooking.getStatus() == BookingStatus.WAITING) {
//                nextBooking.setStatus(BookingStatus.APPROVED);
//                bookingRepository.save(nextBooking);
//                unbookItem(itemId);
//            }
//        }
//    }


}
