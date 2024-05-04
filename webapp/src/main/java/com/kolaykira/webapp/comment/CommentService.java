package com.kolaykira.webapp.comment;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.kolaykira.webapp.menu.Menu;
import com.kolaykira.webapp.menu.MenuService;
import com.kolaykira.webapp.restaurant.Restaurant;
import com.kolaykira.webapp.restaurant.RestaurantService;
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

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    /**
     * To add comment
     * */
    public String addComment(Comment comment)
            throws ExecutionException, InterruptedException {
        Menu m = menuService.getMenuById(comment.getMenuID());
        return saveContractToFirebase(comment);
    }


    /**
     * To delete contract by a given commentId
     * */
    public String deleteComment(String commentId) throws ExecutionException, InterruptedException {
        if (commentId == null || commentId.isEmpty()) {
            throw new IllegalArgumentException("Restaurant ID must be provided");
        }
        // Delete comment from Firebase or your preferred data store
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> deleteApiFuture = dbFireStore.collection(COLLECTION_NAME).document(commentId).delete();
        deleteApiFuture.get();
        return "Restaurant deleted successfully";
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

    public List<Comment> getCommentsByMenuID(String menuID) throws InterruptedException, ExecutionException {
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
     * Helper Function to save the Restaurant
     * */
    public String saveContractToFirebase(Comment c) throws ExecutionException, InterruptedException
    {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(c.getCommentID()).set(c);
        return collectionAPIFuture.get().getUpdateTime().toString();
    }

    public List<CommentShowcase> getCommentShowcaseByUser(String userEmail) throws ExecutionException, InterruptedException {
        List<Comment> comments =  getCommentsByUser( userEmail);
        System.out.println( comments);
        return transformCommentToCommentShowcase(comments);
    }

    public List<CommentShowcase> getCommentShowcaseByCommentID(String commentID) throws ExecutionException, InterruptedException {
        List<Comment> comments =  getCommentsByMenuID(commentID);
        return transformCommentToCommentShowcase(comments);
    }
    public List<CommentShowcase> transformCommentToCommentShowcase(List<Comment> comments) throws ExecutionException, InterruptedException {
        List<CommentShowcase> commentShowcases = new ArrayList<>();
        if (comments == null)
        {
            return commentShowcases;
        }
        for(int i = 0; i < comments.size(); i++)
        {
            Menu m = menuService.getMenuById(comments.get(i).getMenuID() );
            if( m != null  )
            {
                commentShowcases.add(new CommentShowcase(comments.get(i), userService.getUserByEmail(comments.get(i).getUserEmail()).getName(), m.getMenuTitle()  ));
            }
            else
            {
                Restaurant r = restaurantService.getRestaurantByID( comments.get(i).getMenuID());
               if( r != null)
               {
                   commentShowcases.add(new CommentShowcase(comments.get(i), userService.getUserByEmail(comments.get(i).getUserEmail()).getName(), r.getRestaurantTitle() ) );
               }
            }
        }
        return commentShowcases;
    }
}

