package com.assignment.hotel.services;

import com.assignment.hotel.entites.Hotel;
import com.assignment.hotel.payload.ApiResponse;

import java.util.List;

public interface HotelService {

    //create

    Hotel create(Hotel hotel);

    //get all
    List<Hotel> getAll();

    //get single
    Hotel get(String id);

    ApiResponse updateHotel(String hotelId,Hotel hotel);

    ApiResponse deleteHotel(String hotelId);

}
