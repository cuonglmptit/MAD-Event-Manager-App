package com.cuonglmptit.SpringServer.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDAO {
    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(user -> {
            users.add(user);
        });
        return users;
    }
    //check username
    public List<String> checkUsername(String username){
        return repository.checkUsername(username);
    }
    public String checkUserPasswordByUsername(String username){
        return repository.checkUserPasswordByUsername(username);
    }

    public User getUserDetailsByUsername(String username){
        return repository.findByUsername(username);
    }

    public User getUserByUserId(int id){
        return repository.findById(id).get();
    }
}
