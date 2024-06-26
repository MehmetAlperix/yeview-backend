package com.kolaykira.webapp.restaurant;

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
public class RestaurantService {
    private final String COLLECTION_NAME = "restaurant";
    private final UserService userService;

    /**
     * To add restaurant
     * */
    public String addRestaurant(Restaurant restaurant)
            throws ExecutionException, InterruptedException {
        return saveContractToFirebase(restaurant);
    }



    /**
     * To delete contract by a given restaurantID
     * */
    public String deleteComment(String restaurantID) throws ExecutionException, InterruptedException {
        if (restaurantID == null || restaurantID.isEmpty()) {
            throw new IllegalArgumentException("Restaurant ID must be provided");
        }
        // Delete comment from Firebase or your preferred data store
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> deleteApiFuture = dbFireStore.collection(COLLECTION_NAME).document(restaurantID).delete();
        deleteApiFuture.get();
        return "Restaurant deleted successfully";
    }



    /**
     * To get the specific contracts by contractId
     * @param id
     * */
    public Restaurant getRestaurantByID(String id) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists() )
        {
            return document.toObject(Restaurant.class);
        }
        else
        {
            return null;
        }
    }

    /**
     * To get the contracts with specific property owner
     * */
    public List<Restaurant> getRestaurantByOwner(String owner) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        Query query = contractsCollection.whereEqualTo("owner", owner);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        List<Restaurant> restaurants = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            restaurants.add(document.toObject(Restaurant.class));
        }

        return restaurants;
    }

    /**
     * To get the all contracts
     * */
    public List<Restaurant> getRestaurants() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = contractsCollection.get();
        QuerySnapshot querySnapshot = future.get();
        List<Restaurant> restaurants = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            restaurants.add(document.toObject(Restaurant.class));
        }
        return restaurants;
    }

    /**
     * Helper Function to save the Restaurant
     * */
    public String saveContractToFirebase(Restaurant c) throws ExecutionException, InterruptedException
    {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(c.getRestaurantID()).set(c);
        return collectionAPIFuture.get().getUpdateTime().toString();
    }


    public String rate(String restaurantID, long newRating) throws ExecutionException, InterruptedException {
        Restaurant r = getRestaurantByID(restaurantID);
        long newNewRating = (r.getRating() * r .getNumberOfRatings() + newRating ) / ( r.getNumberOfRatings() + 1 );
        r.setRating(newNewRating);
        r.setNumberOfRatings(r.getNumberOfRatings() + 1);
        addRestaurant(r);
        return "Successfull";
    }
    public String editRestaurant(RestaurantEditRequest request) throws ExecutionException, InterruptedException {
        Restaurant r = getRestaurantByID(request.getRestaurantID());
        r.setRestaurantTitle(request.getRestaurantTitle());
        r.setText(request.getText());
        r.setImageURL(request.getImageURL());
        addRestaurant(r);
        return "Done successfully";
    }
}

