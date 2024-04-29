package com.kolaykira.webapp.comment;

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
public class Comment {

    @Id
    private String commentID;
    private String userEmail;
    private long rating;
    // Tenant Info
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp commentDate;

    private String menuID;

    @PrePersist
    public void generateContractId() {
        if (commentID == null) {
            commentID = UUID.randomUUID().toString();
        }
    }
    public Comment(String text, Timestamp commentDate, String userEmail, long rating, String menuID) {
        this.text = text;
        this.commentDate = commentDate;
        this.userEmail = userEmail;
        this.rating = rating;
        this.menuID = menuID;
        generateContractId();
    }

}
