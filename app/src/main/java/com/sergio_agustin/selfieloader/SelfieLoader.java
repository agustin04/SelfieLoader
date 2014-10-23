package com.sergio_agustin.selfieloader;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sergio on 23/10/2014.
 */
public class SelfieLoader {
    public static final String CLIENT_ID = "3e2a17ea0e2d45ffa303f4220ccf880f";
    public static final String INSTAGRAM_SELFIE_HASH_URL = "https://api.instagram.com/v1/tags/selfie/media/recent?client_id="+CLIENT_ID;

    private InstagramObject instagramObject;
    private InstagramJsonParser instagramJsonParser;

    public SelfieLoader() {
        instagramJsonParser = new InstagramJsonParser();
    }

    public void retrieveFirstTimeSelfieData(){
        Log.i("INSTAGRAM","retrieveFirstTimeSelfieData");

        retrieveSelfieData(INSTAGRAM_SELFIE_HASH_URL);


    }

    public void retrieveMoreSelfieData(){
        String nextPictures = instagramObject.getPaginationUrl();
        if(nextPictures != null){
            Log.i("INSTAGRAM","retrieve MORE SelfieData");
            retrieveSelfieData(nextPictures);
        }
    }

    public void retrieveSelfieData(String url){
        try {

            Log.i("INSTAGRAM","retrieveSelfieData");
            URL u = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");

            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            line = stringBuilder.toString();

            Log.i("INSTAGRAM","getting InputStream");

            Log.i("INSTAGRAM","Calling Json Parser");
            instagramObject = instagramJsonParser.parseJson(line);

        }
        catch(Throwable t) {
            t.printStackTrace();
        }

    }

    public ArrayList<InstagramData> getInstagramData(){
        return instagramObject.getData();
    }

    public ArrayList<String> getSelfieImages(ArrayList<String> list, Image.ImageType type){
        Log.i("INSTAGRAM","SelfieLoader - Getting selfie images type:"+type);
        ArrayList<String> selfies = list;
        ArrayList<InstagramData> data = instagramObject.getData();
        Image temp = null;
        for(int i=0; i < data.size(); i++){
            switch(type){
                case LOW_RES:
                    temp = data.get(i).getLowRes();
                    break;
                case STD_RES:
                    temp = data.get(i).getStdRes();
                    break;
                case THUMB:
                    temp = data.get(i).getThumb();
                    break;
            }
            selfies.add(temp.getUrl());
        }

        return selfies;
    }

    public ArrayList<String> getSelfiePatternImages(ArrayList<String> list){
        ArrayList<String> selfies = list;
        ArrayList<InstagramData> data = instagramObject.getData();
        Image temp = null;
        for(int i=0; i < data.size(); i++){

        }

        return selfies;
    }

    public InstagramObject getInstagramObject() {
        return instagramObject;
    }

    public void setInstagramObject(InstagramObject instagramObject) {
        this.instagramObject = instagramObject;
    }
}
