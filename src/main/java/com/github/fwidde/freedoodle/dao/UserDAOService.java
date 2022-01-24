package com.github.fwidde.freedoodle.dao;

import com.github.fwidde.freedoodle.model.User;
import com.github.fwidde.freedoodle.repositories.UserRepository;
import com.github.fwidde.freedoodle.util.UserNotFoundException;
import lombok.extern.flogger.Flogger;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Flogger
public class UserDAOService {

    private final UserRepository userRepository;

    public UserDAOService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByPrincipal(Principal principal){
        return userRepository.findUserByName(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
    }
}
