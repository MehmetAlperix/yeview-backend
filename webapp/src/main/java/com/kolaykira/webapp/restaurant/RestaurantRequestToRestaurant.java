package com.kolaykira.webapp.restaurant;

import com.kolaykira.webapp.Config.Time;
import com.kolaykira.webapp.Config.TimestampConverter;

import java.util.concurrent.ExecutionException;

public class RestaurantRequestToRestaurant {

    public static Restaurant requestToContract(RestaurantRequest request) throws ExecutionException, InterruptedException {
         String owner = request.getOwner();
         long rating = request.getRating();
        // Tenant Info
        String text = request.getText();
        String restaurantTitle = request.getRestaurantTitle();
        return new Restaurant(text,  TimestampConverter.convertJavaSqlTimestamp(Time.getCurrentTimestamp() ),  owner,  rating, restaurantTitle, request.getImageURL());
    }
}
