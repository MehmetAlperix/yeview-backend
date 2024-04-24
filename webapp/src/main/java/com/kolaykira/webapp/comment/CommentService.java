package com.kolaykira.webapp.comment;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.kolaykira.webapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Service
public class CommentService {
    private final String COLLECTION_NAME = "comment";
    private final UserService userService;

    /**
     * To add comment
     * */
    public String addComment(Comment comment)
            throws ExecutionException, InterruptedException {
        return saveContractToFirebase(comment);
    }


    /**
     * To delete contract by a given commentId
     * */
    public String deleteComment(String commentId) throws ExecutionException, InterruptedException {
        if (commentId == null || commentId.isEmpty()) {
            throw new IllegalArgumentException("Comment ID must be provided");
        }
        // Delete comment from Firebase or your preferred data store
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> deleteApiFuture = dbFireStore.collection(COLLECTION_NAME).document(commentId).delete();
        deleteApiFuture.get();
        return "Comment deleted successfully";
    }



    /**
     * To get the specific contracts by contractId
     * @param id
     * */
    public Comment getCommentsById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists() )
        {
            return document.toObject(Comment.class);
        }
        else
        {
            return null;
        }
    }

    /**
     * To get the contracts with specific property owner
     * */
    public List<Comment> getCommentsByUser(String userEmail) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        Query query = contractsCollection.whereEqualTo("userEmail", userEmail);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        List<Comment> comments = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            comments.add(document.toObject(Comment.class));
        }

        return comments;
    }

    public List<Comment> getCommentsByMenu(String menuID) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        Query query = contractsCollection.whereEqualTo("menuID", menuID);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        List<Comment> comments = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            comments.add(document.toObject(Comment.class));
        }

        return comments;
    }


    /**
     * To get the all contracts
     * */
    public List<Comment> getComments() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = contractsCollection.get();
        QuerySnapshot querySnapshot = future.get();
        List<Comment> comments = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            comments.add(document.toObject(Comment.class));
        }
        return comments;
    }

    /**
     * Helper Function to save the Comment
     * */
    public String saveContractToFirebase(Comment c) throws ExecutionException, InterruptedException
    {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(c.getCommentId()).set(c);
        return collectionAPIFuture.get().getUpdateTime().toString();
    }

    public List<CommentShowcase> getCommentShowcaseByUser(String userEmail) throws ExecutionException, InterruptedException {
        List<Comment> comments =  getCommentsByUser( userEmail);
        return transformCommentToCommentShowcase(comments);
    }

    public List<CommentShowcase> getCommentShowcaseByMenuID(String menuID) throws ExecutionException, InterruptedException {
        List<Comment> comments =  getCommentsByMenu(menuID);
        return transformCommentToCommentShowcase(comments);
    }
    public List<CommentShowcase> transformCommentToCommentShowcase(List<Comment> comments) throws ExecutionException, InterruptedException {
        List<CommentShowcase> commentShowcases = new ArrayList<>();
        for(int i = 0; i < comments.size(); i++)
        {
            commentShowcases.add(new CommentShowcase(comments.get(i), userService.getUserByEmail(comments.get(i).getUserEmail()).getName() ));
        }
        return commentShowcases;
    }
}

