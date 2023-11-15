package com.assignment.rating.services.impl;

import com.assignment.rating.entities.Rating;
import com.assignment.rating.exceptions.ResourceNotFoundException;
import com.assignment.rating.payload.ApiResponse;
import com.assignment.rating.repository.RatingRepository;
import com.assignment.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {


    @Autowired
    private RatingRepository repository;

    @Override
    public Rating create(Rating rating) {
        return repository.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return repository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        List<Rating> ratings = repository.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found with user id !! : " + userId);
        }
        return ratings;
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        List<Rating> ratings = repository.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("No ratings found for hotel id !! : " + hotelId);
        }
        return ratings;
    }

    @Override
    public ApiResponse updateRating(String ratingId,Rating updatedRating) {
        if(repository.existsById(ratingId)) {

            Rating currentRating=repository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("Rating with given id is not found on server !! : " + ratingId));;

            if(updatedRating.getRating()!=0){
                currentRating.setRating(updatedRating.getRating());
            }
            if(!updatedRating.getFeedback().isEmpty()){
                currentRating.setFeedback(updatedRating.getFeedback());
            }

            repository.save(currentRating);
            return ApiResponse.builder()
                    .message("User updated successfully!!")
                    .success(true)
                    .status(HttpStatus.FOUND)
                    .build();
        }
        else{
            throw new ResourceNotFoundException("Rating with given id is not found on server !! : " + ratingId);
        }
    }

    @Override
    public ApiResponse deleteRating(String ratingId){
        if(repository.existsById(ratingId)) {
            repository.deleteById(ratingId);
            return ApiResponse.builder()
                    .message("Rating deleted successfully!!")
                    .success(true)
                    .status(HttpStatus.FOUND)
                    .build();
        }
        else{
            throw new ResourceNotFoundException("Rating with given id is not found on server !! : " + ratingId);
        }
    }

}
