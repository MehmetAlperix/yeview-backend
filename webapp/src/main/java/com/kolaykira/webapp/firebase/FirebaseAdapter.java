package com.kolaykira.webapp.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirebaseAdapter {

    /**
     *
     * @param collectionName: Name of the collection
     * @param c name of the class
     * @return the given documents data as class given. If documented class does not match the class given error is given
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static List giveAllInstances(String collectionName, Class c) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collection = dbFirestore.collection(collectionName);

        ApiFuture<QuerySnapshot> future = collection.get();
        QuerySnapshot querySnapshot = future.get();
        List list = new ArrayList<>();

        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            list.add(document.toObject( c ));
        }
        return list;
    }
}
