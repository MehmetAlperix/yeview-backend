package com.kolaykira.webapp.comment;

import com.google.cloud.Timestamp;
import jakarta.persistence.*;


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
    private String menuTitle;

    public CommentShowcase(Comment comment, String userName, String menuTitle) {
        this.commentId = comment.getCommentID();
        this.userEmail = comment.getUserEmail();
        this.rating = comment.getRating();
        this.menuTitle = menuTitle;
        this.text = comment.getText();
        this.commentDate = comment.getCommentDate();
        this.userName = userName ;
        this.menuID = comment.getMenuID();
    }


}
