package org.but.feec.traintransportmanagement.services;

import org.but.feec.traintransportmanagement.api.UserView;
import org.but.feec.traintransportmanagement.data.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public void userService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserService(UserRepository userRepository) {   //defaultni konstruktor, neobsahuje nic, musi tu byt
    }

    public UserView getUserView(int id) {
        return userRepository.findUserView(id);
    }

}
