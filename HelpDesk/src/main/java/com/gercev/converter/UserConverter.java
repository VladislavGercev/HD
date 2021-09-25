package com.gercev.converter;

import com.gercev.domain.User;
import com.gercev.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserDto convert(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convert(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }


}
