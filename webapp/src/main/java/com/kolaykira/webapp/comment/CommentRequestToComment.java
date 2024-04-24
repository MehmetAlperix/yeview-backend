package com.kolaykira.webapp.comment;

import com.kolaykira.webapp.Config.Time;
import com.kolaykira.webapp.Config.TimestampConverter;

import java.util.concurrent.ExecutionException;

public class CommentRequestToComment {

    public static Comment requestToContract(CommentRequest request) throws ExecutionException, InterruptedException {
         String userEmail = request.getUserEmail();
         long rating = request.getRating();
        // Tenant Info
        String text = request.getText();
        String menuID = request.getMenuID();
        return new Comment(text, TimestampConverter.convertJavaSqlTimestamp(Time.getCurrentTimestamp() ), userEmail, rating,menuID );
    }
}
