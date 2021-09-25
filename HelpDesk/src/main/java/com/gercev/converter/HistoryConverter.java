package com.gercev.converter;

import com.gercev.domain.History;
import com.gercev.dto.HistoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryConverter {

    @Autowired
    private ModelMapper modelMapper;

    public HistoryDto convert(History history) {
        return modelMapper.map(history, HistoryDto.class);
    }

    public History convert(HistoryDto historyDto) {
        return modelMapper.map(historyDto, History.class);
    }
}
