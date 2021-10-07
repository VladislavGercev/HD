package com.gercev.converter;

import com.gercev.domain.Comment;
import com.gercev.dto.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    private ModelMapper modelMapper;

    @Autowired
    public CommentConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDto convert(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public Comment convert(CommentDto commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}
