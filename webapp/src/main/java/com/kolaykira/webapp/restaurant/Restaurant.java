package com.kolaykira.webapp.restaurant;

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
public class Restaurant {

    @Id
    private String restaurantID;
    private String owner;
    private long rating;
    // Tenant Info
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createDate;

    private String restaurantTitle;
    private int numberOfRatings;
    private String imageURL;

    @PrePersist
    public void generateContractId() {
        if (restaurantID == null) {
            restaurantID = UUID.randomUUID().toString();
        }
    }
    public Restaurant(String text, Timestamp createDate, String owner, long rating, String restaurantTitle, String imageURL) {
        this.text = text;
        this.createDate = createDate;
        this.owner = owner;
        this.rating = rating;
        this.restaurantTitle = restaurantTitle;
        this.numberOfRatings = 0;
        this.imageURL = imageURL;
        generateContractId();
    }

}
