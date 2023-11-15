package com.assignment.hotel.services.impl;

import com.assignment.hotel.entites.Hotel;
import com.assignment.hotel.exceptions.ResourceNotFoundException;
import com.assignment.hotel.payload.ApiResponse;
import com.assignment.hotel.respositories.HotelRepository;
import com.assignment.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        Random random = new Random();
        String hotelId = "H"+String.valueOf(random.nextInt(101,999));
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id is not found on server !! : " + id));
    }

    @Override
    public ApiResponse updateHotel(String hotelId,Hotel updatedHotel) {
        if(hotelRepository.existsById(hotelId)) {

            Hotel currentHotel=get(hotelId);

            if(!updatedHotel.getName().isEmpty()){
                currentHotel.setName(updatedHotel.getName());
            }
            if(!updatedHotel.getLocation().isEmpty()){
                currentHotel.setLocation(updatedHotel.getLocation());
            }
            if(!updatedHotel.getAbout().isEmpty()){
                currentHotel.setAbout(updatedHotel.getAbout());
            }

            hotelRepository.save(currentHotel);
            return ApiResponse.builder()
                    .message("Hotel updated successfully!!")
                    .success(true)
                    .status(HttpStatus.FOUND)
                    .build();
        }
        else{
            throw new ResourceNotFoundException("Hotel with given id is not found on server !! : " + hotelId);
        }
    }
    
    @Override
    public ApiResponse deleteHotel(String hotelId){
        if(hotelRepository.existsById(hotelId)) {
            hotelRepository.deleteById(hotelId);
            return ApiResponse.builder()
                    .message("Hotel deleted successfully!!")
                    .success(true)
                    .status(HttpStatus.FOUND)
                    .build();
        }
        else{
            throw new ResourceNotFoundException("Hotel with given id is not found on server !! : " + hotelId);
        }
    }
}
