package com.gercev.service.enums;

import com.gercev.domain.User;
import com.gercev.exception.UserNotFoundException;
import com.gercev.service.UserService;

import java.util.List;

public enum Recipient {

    FOR_NEW {
        @Override
        public List<User> getRecipients(UserService userService) {
            return userService.getManagers().orElseThrow(() -> new UserNotFoundException(""));
        }
    }, FOR_APPROVED {
        @Override
        public List<User> getRecipients(UserService userService) {
            return userService.getManagers().orElseThrow(() -> new UserNotFoundException(""));
        }
    }, FOR_DECLINED {
        @Override
        public List<User> getRecipients(UserService userService) {
            return userService.getManagers().orElseThrow(() -> new UserNotFoundException(""));
        }
    }, FOR_CANCELED {
        @Override
        public List<User> getRecipients(UserService userService) {
            return userService.getManagers().orElseThrow(() -> new UserNotFoundException(""));
        }
    }, FOR_CANCELED_FROM_NEW {
        @Override
        public List<User> getRecipients(UserService userService) {
            return userService.getManagers().orElseThrow(() -> new UserNotFoundException(""));
        }
    }, FOR_DONE {
        @Override
        public List<User> getRecipients(UserService userService) {
            return userService.getManagers().orElseThrow(() -> new UserNotFoundException(""));
        }
    };


    public abstract List<User> getRecipients(UserService userService);

}
