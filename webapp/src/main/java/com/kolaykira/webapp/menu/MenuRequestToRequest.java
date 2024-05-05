package com.kolaykira.webapp.menu;

import com.kolaykira.webapp.Config.Time;
import com.kolaykira.webapp.Config.TimestampConverter;

import java.util.concurrent.ExecutionException;

public class MenuRequestToRequest {

    public static Menu requestToContract(MenuRequest request) throws ExecutionException, InterruptedException {
        String ingredients = request.getIngredients();
        String context = request.getContext();
        long rating = request.getRating();
        String RestaurantID = request.getRestaurantID();
        String menuTitle = request.getMenuTitle();
       return new Menu(menuTitle, ingredients,  TimestampConverter.convertJavaSqlTimestamp(Time.getCurrentTimestamp()), context,  rating,  RestaurantID, request.getImageURL() );
    }
}
