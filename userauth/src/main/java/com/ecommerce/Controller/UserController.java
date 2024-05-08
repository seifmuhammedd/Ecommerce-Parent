package com.ecommerce.Controller;

import com.ecommerce.DTO.LoginDTO;
import com.ecommerce.Model.User;
import com.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user)
    {
        String userName = user.getUserName();

        Boolean IfUserExists =  userService.findUserByUserName(userName);

        if(IfUserExists)
        {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Username is already used, Try another one.");
            return ResponseEntity.ok().body(response);
        }

        Map<String,Object> response = userService.saveUserToDB(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginDTO loginDTO) {
        String name = loginDTO.getUserName();
        String password = loginDTO.getPassword();

        Map<String,Object> response = userService.Login(name, password);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id)
    {
        User user = userService.FindUserByUserId(id);
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }

}
