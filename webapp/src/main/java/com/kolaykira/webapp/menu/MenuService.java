package com.kolaykira.webapp.menu;

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
public class MenuService {
    private final String COLLECTION_NAME = "menu";
    private final UserService userService;

    /**
     * To add menu
     * */
    public String addMenu(Menu menu)
            throws ExecutionException, InterruptedException {
        return saveMenuToFirebase(menu);
    }


    /**
     * To delete menu by a given commentId
     * */
    public String deleteMenu(String menuID) throws ExecutionException, InterruptedException {
        if (menuID == null || menuID.isEmpty()) {
            throw new IllegalArgumentException("Menu ID must be provided");
        }
        // Delete comment from Firebase or your preferred data store
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> deleteApiFuture = dbFireStore.collection(COLLECTION_NAME).document(menuID).delete();
        deleteApiFuture.get();
        return "Menu deleted successfully";
    }



    /**
     * To get the specific menus by restaurantID
     * @param id
     * */
    public Menu getMenuById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists() )
        {
            return document.toObject(Menu.class);
        }
        else
        {
            return null;
        }
    }

    /**
     * To get the contracts with specific property owner
     * */
    public List<Menu> getMenusByRestaurant(String restaurantID) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        Query query = contractsCollection.whereEqualTo("restaurantID", restaurantID);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot querySnapshot = future.get();
        List<Menu> menus = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            menus.add(document.toObject(Menu.class));
        }

        return menus;
    }




    /**
     * To get the all menus
     * */
    public List<Menu> getMenu() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference contractsCollection = dbFirestore.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> future = contractsCollection.get();
        QuerySnapshot querySnapshot = future.get();
        List<Menu> menus = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            menus.add(document.toObject(Menu.class));
        }
        return menus;
    }

    /**
     * Helper Function to save the Menu
     * */
    public String saveMenuToFirebase(Menu c) throws ExecutionException, InterruptedException
    {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(c.getMenuID()).set(c);
        return collectionAPIFuture.get().getUpdateTime().toString();
    }

    public String editMenu(MenuEditRequest requestToContract) throws ExecutionException, InterruptedException {

        Menu m = getMenuById( requestToContract.getMenuID() );
        m.setMenuTitle(requestToContract.getMenuTitle());
        m.setContext(requestToContract.getContext());
        m.setRating(requestToContract.getRating());
        m.setIngredients(requestToContract.getIngredients());
        m.setRestaurantID(requestToContract.getRestaurantID());
        saveMenuToFirebase(m);
        return "Successfully edited menu";
    }
}

