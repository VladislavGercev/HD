package com.gercev.converter;

import com.gercev.domain.Attachment;
import com.gercev.dto.AttachmentDto;
import com.gercev.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentConverter {

    @Autowired
    private ModelMapper modelMapper;

    public AttachmentDto convert(Attachment attachment){
        return modelMapper.map(attachment,AttachmentDto.class);
    }
    public Attachment convert(AttachmentDto attachmentDto){
        return modelMapper.map(attachmentDto,Attachment.class);
    }


}
