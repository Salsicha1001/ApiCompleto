package com.borges.api_complete.Firebase;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FireInit {

    @PostConstruct
    public void Initalize() throws IOException {

        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("####");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("######")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public Firestore getdb(){
        return FirestoreClient.getFirestore();
    }
}
