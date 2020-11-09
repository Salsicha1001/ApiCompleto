package com.borges.api_complete.Service;

import com.borges.api_complete.Firebase.FireInit;
import com.borges.api_complete.Model.DTO.CardDTO;
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



    private String url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?";
    private String ptbr = "&language=pt";

    public Object getCardsUser(String email){
        try {
            ApiFuture<QuerySnapshot> future =
                    fireInit.getdb().collection("cardsUser").whereEqualTo("email", email).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            List<CardDTO> card = new ArrayList<>();
            for (DocumentSnapshot document : documents) {
                System.out.println(document.getId() + " => " + document.toObject(CardDTO.class));
                card.add(document.toObject(CardDTO.class));
            }

            return card;
        }catch (Error e){
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object saveCardUser(CardDTO card) throws ExecutionException, InterruptedException {
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

    public Object FindMonsterCard(String type, String race) throws IOException, JSONException {
        if(type == null && race ==null){
            return false;
        }else if(race == null){
        type = type.replace(" ","%20");
        JSONObject jsonObject = readJsonFromUrl(url+"type="+type+ptbr);
        JSONArray j = jsonObject.getJSONArray("data");
        return j.toString();
        }else if(type ==null){
            race = race.replace(" ","%20");
            JSONObject jsonObject = readJsonFromUrl(url+"race="+race+ptbr);
            JSONArray j = jsonObject.getJSONArray("data");
            return j.toString();
        }else if(VerifyHttp(type, race)==200){
            type = type.replace(" ","%20");
            race = race.replace(" ","%20");
            JSONObject j = readJsonFromUrl(url+"type="+type+"&race="+race+ptbr);
            JSONArray jsonArray = j.getJSONArray("data");
            return jsonArray.toString();
        }
        return false;
    }



    public String findSpellCard() throws IOException, JSONException {
        JSONObject jsonObject = readJsonFromUrl(url+"type=spell%20card"+ ptbr);
        JSONArray j = jsonObject.getJSONArray("data");
        return j.toString();
    }
    public Object findAll(Integer num, Integer offset) throws IOException, JSONException {
        PageRequest pageRequest=PageRequest.of(offset, num);

        JSONObject jsonObject = readJsonFromUrl(url+"num="+pageRequest.getPageSize()+"&offset="+pageRequest.getPageNumber()+ ptbr);
        return jsonObject.toString();

    }

    public String findTrapCard() throws IOException, JSONException {
        JSONObject jsonObject = readJsonFromUrl(url+"type=trap%20card"+ ptbr);
        JSONArray j = jsonObject.getJSONArray("data");
        return j.toString();
    }
    public Object findRaceSpell(String race) throws IOException, JSONException {
        if(VerifyHttp("spell card", race)== 200) {
            JSONObject jsonObject = readJsonFromUrl(url + "type=spell%20card&race=" + race + ptbr);
            JSONArray j = jsonObject.getJSONArray("data");
            return j.toString();
        }else{
            return false;
        }

    }
    public Object findRaceTrap(String race) throws IOException, JSONException {
        if(VerifyHttp("trap card", race)== 200) {
            JSONObject jsonObject = readJsonFromUrl(url + "type=trap%20card&race=" + race + ptbr);
            JSONArray j = jsonObject.getJSONArray("data");
            return j.toString();
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

