package com.kolaykira.webapp.restaurant;



import com.google.cloud.Timestamp;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEditRequest {
    private String restaurantID;
    // Tenant Info
    private String text;
    private String restaurantTitle;
    private String imageURL;
}
