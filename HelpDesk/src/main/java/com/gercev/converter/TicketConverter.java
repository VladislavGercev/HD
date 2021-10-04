package com.gercev.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gercev.domain.Ticket;
import com.gercev.dto.TicketDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class TicketConverter {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;

    public TicketDto convert(Ticket ticket) {
        return modelMapper.map(ticket, TicketDto.class);
    }

    public Ticket convert(TicketDto ticketDto) {
        return modelMapper.map(ticketDto, Ticket.class);
    }

    public TicketDto convert(String ticketJson) {
        TicketDto ticketDto = new TicketDto();
        if (!ticketJson.isEmpty()) {
            try {
                ticketDto = objectMapper.readValue(ticketJson, TicketDto.class);
            } catch (IOException e) {
                throw new IllegalArgumentException("It is not possible to convert JSON");
            }
        }
        return ticketDto;
    }
}
