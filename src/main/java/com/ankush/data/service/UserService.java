package com.ankush.data.service;

import com.ankush.data.entities.User;
import com.ankush.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<String> getAllUserNames(){
        return repository.getAllUsernames();
    }

    public User getByUsername(String username){
        if(repository.getByUsername(username)!=null)
            return repository.getByUsername(username);
        else
            return null;
    }
    public int saveUser(User user)
    {
        repository.save(user);
        return 1;
    }
}
