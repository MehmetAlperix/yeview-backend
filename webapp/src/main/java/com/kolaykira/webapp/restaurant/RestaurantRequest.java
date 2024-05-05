package com.kolaykira.webapp.restaurant;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {
    private String owner;
    private long rating;
    // Tenant Info
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createDate;
    private String restaurantTitle;
    private String imageURL;
}
