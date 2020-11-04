package com.borges.api_complete.Service;


import com.borges.api_complete.Firebase.FireInit;
import com.borges.api_complete.Model.UserModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {


    @Autowired
    private FireInit fireInit;
@Autowired
private BCryptPasswordEncoder pe;


    public Object insertAuth(UserModel u) throws FirebaseAuthException {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(u.getEmail())
                    .setEmailVerified(false)
                    .setPassword(u.getPassword())
                    .setDisplayName(u.getFirstName() + " " + u.getLastName())
                    .setDisabled(false);


            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return userRecord;
        }catch (Error e){
            return false;
        }
    }


    public Object InsertUser(UserModel u) throws ExecutionException, InterruptedException, FirebaseAuthException {
        try {


            u.setPassword(pe.encode(u.getPassword()));
            ApiFuture<DocumentReference> future = fireInit.getdb().collection("users").add(u);
            System.out.println("Added document with ID: " + future.get().getId());
            setID(future.get().getId());
            return insertAuth(u);
        }catch (Error e){
            return false;
        }
    }

    public List findAll() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> user = fireInit.getdb().collection("users").get();
        List<QueryDocumentSnapshot> documents = user.get().getDocuments();
        List<UserModel> userModels = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            UserModel u =  document.toObject(UserModel.class);
            //System.out.println(u);
            userModels.add(u);

        }
        ;
      return userModels;
    }

    public UserModel findUserByEmail(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> u = fireInit.getdb().collection("users").whereEqualTo("email", email).get();
        UserModel user = new UserModel();
        List<QueryDocumentSnapshot> documents = u.get().getDocuments();
        for (DocumentSnapshot document : documents) {
           // System.out.println(document.getId() + " => " + document.toObject(UserModel.class));
            user = document.toObject(UserModel.class);
        }
    return user;
    }



    private void setID(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef  = fireInit.getdb().collection("users").document(id);
        ApiFuture<WriteResult> future = docRef.update("id", id);
      //  WriteResult result = future.get();
       // System.out.println("Write result: " + result);

    }

    public boolean deleteUser(String id) {

        ApiFuture<WriteResult> writeResult = fireInit.getdb().collection("users").document(id).delete();

        return true;
    }

    public UserRecord FindEmailFireAuth(String email) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        return userRecord;
    }


    public Object findUserbyIdCloud(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = fireInit.getdb().collection("users").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
           return document.toObject(UserModel.class);
        } else {
            return false;
        }

    }
    public boolean deleteUserFireAuth(String id) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(id);
        return true;
    }

    public UserModel updateUser(UserModel user) throws ExecutionException, InterruptedException, FirebaseAuthException {
        UserModel userModel = (UserModel) findUserbyIdCloud(user.getId());
        UserRecord userRecord = FindEmailFireAuth(userModel.getEmail());
        updateFireAuth(userRecord, user);
        updateCloud(userModel);
        return userModel;

    }
    private UserRecord updateFireAuth(UserRecord user, UserModel userModel) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(user.getUid())

                .setPhoneNumber(userModel.getNumber())
                .setPassword(userModel.getPassword())
                .setPhotoUrl("http://www.example.com/12345678/photo.png");

        UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
        return userRecord;
    }

    private Timestamp updateCloud(UserModel userModel) throws ExecutionException, InterruptedException {
        DocumentReference docRef = fireInit.getdb().collection("users").document(userModel.getId());
        Map<String, Object> updates = new HashMap<>();
        updates.put("birthday", userModel.getBirthday());
        updates.put("number",userModel.getNumber());
        updates.put("password", userModel.getPassword());

        ApiFuture<WriteResult> future = docRef.update(updates);
        return  future.get().getUpdateTime();
    }
}
