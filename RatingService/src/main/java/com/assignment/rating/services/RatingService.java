package com.assignment.rating.services;

import com.assignment.rating.entities.Rating;
import com.assignment.rating.payload.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

    //create
    Rating create(Rating rating);


    //get all ratings
    List<Rating> getRatings();

    //get all by UserId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);

    ApiResponse updateRating(String ratingId,Rating rating);

    ApiResponse deleteRating(String ratingId);

}
