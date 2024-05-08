package com.ecommerce.Service.Impl;

import com.ecommerce.Model.User;
import com.ecommerce.Repo.UserRepo;
import com.ecommerce.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public Boolean findUserByUserName(String userName) {
        User user = userRepo.findByUserName(userName);
        //if User is not in DB return false else return true
        return user != null;

    }

    @Override
    public Map<String, Object> saveUserToDB(User user) {
        User UserSaved = userRepo.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("UserID",UserSaved.getUserId());
        response.put("userType" , UserSaved.getUserType());
        response.put("userName",UserSaved.getUserName());
        response.put("message","Register successful");
        return response;
    }

    @Override
    public User FindUserByUserId(Long userId) {
        return userRepo.findUserByUserId(userId);
    }

    @Override
    public Map<String, Object> Login(String username, String pass) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepo.findByUserName(username);

        if (user != null) {


            if (pass.equals(user.getPassword())) {
                response.put("message", "login successful");
                response.put("usertype", user.getUserType());
                response.put("userID", user.getUserId());
                return response;
            } else {
                response.put("message", "There is an error in username or password");
                return response;
            }
        }
        response.put("message", "Register first");
        return response;
    }
}
