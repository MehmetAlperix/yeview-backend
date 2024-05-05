package com.kolaykira.webapp.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.cloud.Timestamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public class Menu {

    @Id
    private String menuID;
    private String context;

    private String menuTitle;
    private long rating;
    // Tenant Info
    private String ingredients;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp commentDate;
    private int numberOfRatings;
    private String restaurantID;

    private String imageURL;


    @PrePersist
    public void generateContractId() {
        if (menuID == null) {
            menuID = UUID.randomUUID().toString();
        }
    }
    public Menu(String menuTitle, String ingredients, Timestamp commentDate, String context, long rating, String restaurantID, String imageURL) {
        this.ingredients = ingredients;
        this.menuTitle = menuTitle;
        this.commentDate = commentDate;
        this.context = context;
        this.rating = rating;
        this.numberOfRatings = 0;
        this.restaurantID = restaurantID;
        this.imageURL = imageURL;
        generateContractId();
    }
    public Menu(String menuID, String menuTitle, String ingredients, Timestamp commentDate, String context, long rating, String restaurantID) {
        this.ingredients = ingredients;
        this.menuTitle = menuTitle;
        this.commentDate = commentDate;
        this.context = context;
        this.rating = rating;
        this.numberOfRatings = 0;
        this.restaurantID = restaurantID;
        this.menuID = menuID;
        generateContractId();
    }

}
