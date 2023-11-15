package com.assignment.user.services.impl;

import com.assignment.user.entities.Hotel;
import com.assignment.user.entities.User;
import com.assignment.user.entities.Rating;
import com.assignment.user.exceptions.ResourceNotFoundException;
import com.assignment.user.external.services.HotelService;
import com.assignment.user.payload.ApiResponse;
import com.assignment.user.repositories.UserRepository;
import com.assignment.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        Random random = new Random();
        String userId = "U"+String.valueOf(random.nextInt(101,999));
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{} ", ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public ApiResponse updateUser(String userId,User updatedUser) {
        if(userRepository.existsById(userId)) {

            User currentUser=getUser(userId);

            if(!updatedUser.getName().isEmpty()){
                currentUser.setName(updatedUser.getName());
            }
            if(!updatedUser.getEmail().isEmpty()){
                currentUser.setEmail(updatedUser.getEmail());
            }
            if(!updatedUser.getAbout().isEmpty()){
                currentUser.setAbout(updatedUser.getAbout());
            }

            userRepository.save(currentUser);
            return ApiResponse.builder()
                    .message("User updated successfully!!")
                    .success(true)
                    .status(HttpStatus.FOUND)
                    .build();
        }
        else{
            throw new ResourceNotFoundException("User with given id is not found on server !! : " + userId);
        }
    }

    @Override
    public ApiResponse deleteUser(String userId) {
        if(userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return ApiResponse.builder()
                    .message("User deleted successfully!!")
                    .success(true)
                    .status(HttpStatus.FOUND)
                    .build();
        }
        else{
            throw new ResourceNotFoundException("User with given id is not found on server !! : " + userId);
        }
    }
}
