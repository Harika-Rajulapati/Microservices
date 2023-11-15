package com.assignment.user.services;

import com.assignment.user.entities.User;
import com.assignment.user.payload.ApiResponse;

import java.util.List;

public interface UserService {

    //user operations

    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId

    User getUser(String userId);

    ApiResponse updateUser(String userId,User user);

    ApiResponse deleteUser(String userId);




}
