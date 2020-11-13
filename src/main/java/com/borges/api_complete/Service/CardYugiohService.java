package com.borges.api_complete.Service;

import com.borges.api_complete.Firebase.FireInit;
import com.borges.api_complete.Model.CardUser;
import com.borges.api_complete.Model.DTO.CardDTO;
import com.borges.api_complete.Model.UserModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CardYugiohService {

    @Autowired
    private FireInit fireInit;

    @Autowired
    private UserService userService;


    private String url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?";
    private String ptbr = "&language=pt";





    public String findClass(String atribute,Integer num, Integer offset) throws IOException, JSONException {
        atribute = atribute.replace(" ","%20");
        JSONObject jsonObject = readJsonFromUrl(url+"num="+num+"&offset="+offset+"&archetype="+atribute+ ptbr);
        return jsonObject.toString();
    }









    public Object getCardsUser(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = fireInit.getdb().collection("cardsUser").whereEqualTo("user_email", email).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List c = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.getData());
            c.add(document.getData());
        }
        return c;
    }

    public Object deleteFavoriteCard(String id) throws ExecutionException, InterruptedException {
        try {
            ApiFuture<WriteResult> writeResult = fireInit.getdb().collection("cardsUser").document(id).delete();
            return true;
        }catch (Error e){
            return e;
        }
    }


    public Object saveCardUser(CardUser card) throws ExecutionException, InterruptedException {
        try {
            ApiFuture<DocumentReference> add = fireInit.getdb().collection("cardsUser").add(card);
            setID(add.get().getId());
            return true;
        }catch (Error e){
            return false;
        }
    }

    private void setID(String id)  {
        DocumentReference docRef  = fireInit.getdb().collection("cardsUser").document(id);
        ApiFuture<WriteResult> future = docRef.update("id", id);


    }

    public Object FindMonsterCard(String type, String race,
                                  Integer num, Integer offset) throws IOException, JSONException {
        if(type == null && race ==null){
            return false;
        }else if(race == null){
        type = type.replace(" ","%20");
        PageRequest pageRequest=PageRequest.of(offset, num);
        JSONObject jsonObject = readJsonFromUrl(url+"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+"&type="+type+ptbr);

        return jsonObject.toString();
        }else if(type ==null){
            race = race.replace(" ","%20");
            PageRequest pageRequest=PageRequest.of(offset, num);
            JSONObject jsonObject = readJsonFromUrl(url+"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+"&race="+race+ptbr);
            return jsonObject.toString();
        }else if(VerifyHttp(type, race)==200){
            type = type.replace(" ","%20");
            race = race.replace(" ","%20");
            PageRequest pageRequest=PageRequest.of(offset, num);
            JSONObject j = readJsonFromUrl(url+"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+"&type="+type+"&race="+race+ptbr);
            return j.toString();
        }
        return false;
    }



    public String findSpellCard(Integer num, Integer offset) throws IOException, JSONException {

        JSONObject jsonObject = readJsonFromUrl(url+"num="+num+"&offset="+offset+"&type=spell%20card"+ ptbr);
        return jsonObject.toString();
    }



    public Object findAll(Integer num, Integer offset) throws IOException, JSONException {
        PageRequest pageRequest=PageRequest.of(offset, num);

        JSONObject jsonObject = readJsonFromUrl(url+"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+ ptbr);

        return jsonObject.toString();

    }

    public String findTrapCard(Integer num, Integer offset) throws IOException, JSONException {
        PageRequest pageRequest=PageRequest.of(offset, num);
        JSONObject jsonObject = readJsonFromUrl(url+"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+"&type=trap%20card"+ ptbr);
    return jsonObject.toString();
    }
    public Object findRaceSpell(String race,
                                Integer num, Integer offset) throws IOException, JSONException {
        if(VerifyHttp("spell card", race)== 200) {
            PageRequest pageRequest=PageRequest.of(offset, num);
            JSONObject jsonObject = readJsonFromUrl(url +"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+ "&type=spell%20card&race=" + race + ptbr);
        return jsonObject.toString();
        }else{
            return false;
        }

    }
    public Object findRaceTrap(String race,Integer num, Integer offset) throws IOException, JSONException {
        if(VerifyHttp("trap card", race)== 200) {
            PageRequest pageRequest=PageRequest.of(offset, num);
            JSONObject jsonObject = readJsonFromUrl(url +"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+ "&type=trap%20card&race=" + race + ptbr);
            return jsonObject.toString();
        }else{
            return false;
        }

    }

    private int VerifyHttp(String type, String race) throws IOException {
        type = type.replace(" ", "%20");
        race =race.replace(" ","%20");
        URL url = new URL("https://db.ygoprodeck.com/api/v7/cardinfo.php?type="+type+"&race="+race+"&language=pt");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        return responsecode;
    }

    //Leitura de API
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}

