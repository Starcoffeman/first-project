package ru.practicum.shareit.comment;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public class CommentMapper {
    public static CommentDto mapToCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .build();
    }

//    public static List<ItemDto> mapToItemDto(Iterable<Item> items) {
//        List<ItemDto> result = new ArrayList<>();
//
//        for (Item item : items) {
//            result.add(mapToItemDto(item));
//        }
//
//        return result;
//    }

//    public static Item mapToNewItem(ItemDto itemDto) {
//        Item item = new Item();
//        item.setName(itemDto.getName());
//        item.setDescription(itemDto.getDescription());
//        item.setAvailable(itemDto.getAvailable());
//        item.setOwner(0);
//        item.setRequest(0);
//        return item;
//    }
}
