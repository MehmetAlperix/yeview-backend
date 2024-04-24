package com.kolaykira.webapp.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.cloud.Timestamp;
import jakarta.persistence.*;
import lombok.*;




import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentShowcase {

    private String commentId;
    private String userEmail;
    private long rating;
    // Tenant Info
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp commentDate;
    private String userName;
    private String menuID;

    public CommentShowcase(Comment comment, String userName) {
        this.commentId = comment.getCommentId();
        this.userEmail = comment.getUserEmail();
        this.rating = comment.getRating();

        this.text = comment.getText();
        this.commentDate = comment.getCommentDate();
        this.userName = userName ;
        this.menuID = comment.getMenuID();
    }


}
