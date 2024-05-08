package com.ecommerce.Service;

import com.ecommerce.Model.User;

import java.util.Map;

public interface UserService {

    Boolean findUserByUserName(String userName);

     Map<String , Object> saveUserToDB(User user);

     User FindUserByUserId(Long userId);

    Map<String, Object> Login(String username, String pass);
}
