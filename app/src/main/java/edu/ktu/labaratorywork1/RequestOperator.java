package edu.ktu.labaratorywork1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RequestOperator extends Thread {

    public interface  RequestOperatorListener{
        void success (ArrayList<ModelPost> publication);
        void failed (int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;

    public  void setListener (RequestOperatorListener listener){
        this.listener = listener;
    }

    @Override
    public void run(){
        super.run();
        try{
            ArrayList<ModelPost> publication = request();
            if(publication!=null)
                success(publication);
            else
                failed(responseCode);
        }catch (IOException e){
            failed(-1);
        }catch (JSONException e){
            failed(-2);
        }
    }

    private ArrayList<ModelPost> request() throws IOException, JSONException{
        //URL obj = new URL("http://jsonplaceholder.typicode.com/posts/2");
        URL obj = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        responseCode = con.getResponseCode();
        System.out.println("Response Code: "+ responseCode);

        InputStreamReader streamReader;

        if(responseCode == 200){
            streamReader = new InputStreamReader(con.getInputStream());
        } else {
            streamReader = new InputStreamReader(con.getErrorStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null){
            response.append((inputLine));
        }
        in.close();

        System.out.println(response.toString());

        if(responseCode == 200)
            //return parsingJsonObject(response.toString());
            return parsingJsonObjectArray(response.toString());
        else
            return null;
    }

    public ModelPost parsingJsonObject(String response) throws JSONException{

        JSONObject object = new JSONObject(response);
        ModelPost post = new ModelPost();

        post.setId(object.optInt("id",0));
        post.setUserId(object.optInt("userId",0));

        post.setTitle(object.optString("title"));
        post.setBodyText(object.optString("body"));

        return post;
    }

    public ArrayList<ModelPost> parsingJsonObjectArray(String response) throws JSONException {
        //attemtps to create a json object of achieving a response
        JSONArray array = new JSONArray(response);
        ArrayList<ModelPost> posts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            posts.add(new ModelPost(
                    array.getJSONObject(i).optInt("id", 0),
                    array.getJSONObject(i).optInt("userId", 0),
                    array.getJSONObject(i).getString("title"),
                    array.getJSONObject(i).getString("body")));
        }
        return posts;
    }


    private void success(ArrayList<ModelPost> publication){
        if(listener!=null)
            listener.success(publication);
    }
    private void failed(int code){
        if(listener!=null)
            listener.failed(code);
    }


}
