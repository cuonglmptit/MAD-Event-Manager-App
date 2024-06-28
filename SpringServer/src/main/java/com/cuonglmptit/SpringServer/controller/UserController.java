package com.cuonglmptit.SpringServer.controller;

import com.cuonglmptit.SpringServer.model.ResponseData;
import com.cuonglmptit.SpringServer.model.user.Login;
import com.cuonglmptit.SpringServer.model.user.User;
import com.cuonglmptit.SpringServer.model.user.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody Login login) {

        //Get username
        List<String> username = userDAO.checkUsername(login.getUsername());
        //Check if email is empty
        if (username.isEmpty() || username == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseData(HttpStatus.NOT_FOUND.value(), "Username không tồn tại"));
        }
        //Get password
        String password = userDAO.checkUserPasswordByUsername(login.getUsername());
        //Validate
        if (!login.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseData(HttpStatus.BAD_REQUEST.value(), "Sai mật khẩu"));
        }

        //Set user object
        User user = userDAO.getUserDetailsByUsername(login.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(HttpStatus.OK.value(), "Login successful", user));
    }

    @GetMapping("/{idusername}")
    public User getUserById(@PathVariable("idusername") String idusername) {
        System.out.println("Get: " + idusername);
        User user = null;
        try {
            user = userDAO.getUserByUserId(Integer.parseInt(idusername));
        } catch (Exception e) {
        }
        if (user == null) {
            try {
                user = userDAO.getUserDetailsByUsername(idusername);
            } catch (Exception e) {
            }
        }
        System.out.println(user);
        return user;
    }
}
