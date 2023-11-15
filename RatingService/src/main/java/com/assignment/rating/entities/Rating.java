package com.assignment.rating.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("ratings")
public class Rating {

    @Id
    @Field(name="ratingId")
    private String ratingId;

    private String userId;

    private String hotelId;

    private  float rating;

    private  String feedback;
}
