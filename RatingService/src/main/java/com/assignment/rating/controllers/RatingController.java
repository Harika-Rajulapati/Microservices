package com.assignment.rating.controllers;

import com.assignment.rating.entities.Rating;
import com.assignment.rating.payload.ApiResponse;
import com.assignment.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //create rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating) {
        Random random = new Random();
        String ratingId = "R"+String.valueOf(random.nextInt(101,999));
        rating.setRatingId(ratingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    //get all
    @GetMapping
    public ResponseEntity<List<Rating>> getRatings() {
        return ResponseEntity.ok(ratingService.getRatings());
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String ratingId,@RequestBody Rating rating) {
        return ResponseEntity.ok(ratingService.updateRating(ratingId,rating));
    }

    //get all of user
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    //get all of hotels
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<ApiResponse> deleteRating(@PathVariable String ratingId) {
        return ResponseEntity.ok(ratingService.deleteRating(ratingId));
    }


}
