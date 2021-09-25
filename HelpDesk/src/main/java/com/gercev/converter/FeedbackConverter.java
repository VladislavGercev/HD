package com.gercev.converter;

import com.gercev.domain.Feedback;
import com.gercev.dto.FeedbackDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackConverter {

    @Autowired
    ModelMapper modelMapper;

    public Feedback convert(FeedbackDto feedbackDTO) {
        return modelMapper.map(feedbackDTO, Feedback.class);
    }

    public FeedbackDto convert(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }
}
