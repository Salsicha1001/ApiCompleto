package com.borges.api_complete.Controller;


import com.borges.api_complete.Model.UserModel;
import com.borges.api_complete.Service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String InsertUser(@RequestBody UserModel u) throws FirebaseAuthException, ExecutionException, InterruptedException {

        UserRecord o = (UserRecord) userService.InsertUser(u);
        System.out.println(o.getUid());
        return o.getUid();
    }

    @GetMapping
    public Object FindAll() throws ExecutionException, InterruptedException {
        List<UserModel> userModels = new ArrayList<>();
        userModels.addAll(userService.findAll());
        return userModels;
    }

    @GetMapping("/email={email}")
    public Object FindEmail(@PathVariable(value = "email") String email) throws ExecutionException, InterruptedException {
        return userService.findUserByEmail(email);

    }
    @GetMapping("/id={id}")
    public UserModel findUserById(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
    UserModel user = (UserModel) userService.findUserbyIdCloud(id);
        return user;
    }

    @DeleteMapping("/deleteid={id}")
    public boolean deleteUser(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException, FirebaseAuthException {
        UserModel user = (UserModel) userService.findUserbyIdCloud(id);
        UserRecord userRecord = userService.FindEmailFireAuth(user.getEmail());
        userService.deleteUser(user.getId());
        userService.deleteUserFireAuth(userRecord.getUid());
        return true;
    }
    @PutMapping("/update")
    public UserModel updateUser(@RequestBody UserModel userModel) throws InterruptedException, ExecutionException, FirebaseAuthException {
       UserModel user = userService.updateUser(userModel);
        return user;
    }
}
