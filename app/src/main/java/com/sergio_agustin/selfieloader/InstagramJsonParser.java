package com.sergio_agustin.selfieloader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sergio on 23/10/2014.
 */
public class InstagramJsonParser {


    public InstagramObject parseJson(String JSONResp){
        //Log.i("INSTAGRAM","In parseJson method, JsonResp:"+JSONResp);
        InstagramObject instagramObject = new InstagramObject();
        try{
            Log.i("INSTAGRAM","In parseJson method");
            JSONObject reader = new JSONObject(JSONResp);
            //Getting meta data from JSON
            Log.i("INSTAGRAM","In parseJson method, getting meta");
            JSONObject meta = reader.getJSONObject("meta");
            instagramObject.setMetaCode(meta.getInt("code"));
            if(instagramObject.getMetaCode() == 200){
                //Getting Pagination Object
                Log.i("INSTAGRAM","In parseJson method, code 200, getting pagination");
                JSONObject pagination = reader.getJSONObject("pagination");
                instagramObject.setPaginationUrl(pagination.getString("next_url"));

                Log.i("INSTAGRAM","In parseJson method, code 200, getting data array");
                //Now getting all data from JSON. It's on an Array
                JSONArray data = reader.getJSONArray("data");

                ArrayList<InstagramData> list = new ArrayList<InstagramData>();
                for(int i=0; i < data.length(); i++) {
                    JSONObject jo = data.getJSONObject(i);
                    InstagramData instagramData = parseJsonData(jo);
                    list.add(instagramData);
                }
                Log.i("INSTAGRAM","In parseJson method, code 200, setting data arrayList");
                instagramObject.setData(list);

            }else{
                Log.i("INSTAGRAM","In parseJson method, failed code:"+instagramObject.getMetaCode());
            }
        }catch(Exception e){e.printStackTrace();}
        return instagramObject;
    }

    public InstagramData parseJsonData(JSONObject jo){
        InstagramData id = new InstagramData();
        ArrayList<Image> imageList = new ArrayList<Image>();

        try {
            JSONObject images = jo.getJSONObject("images");

            JSONObject lowRes = images.getJSONObject("low_resolution");
            JSONObject thumb = images.getJSONObject("thumbnail");
            JSONObject stdRes = images.getJSONObject("standard_resolution");

            id.setLowRes(setImageFromJson(lowRes, Image.ImageType.LOW_RES));
            id.setThumb(setImageFromJson(thumb, Image.ImageType.THUMB));
            id.setStdRes(setImageFromJson(stdRes, Image.ImageType.STD_RES));

        }catch(Exception e){e.printStackTrace();}
        return id;
    }

    public Image setImageFromJson(JSONObject image, Image.ImageType type){

        Image image1 = new Image();
        image1.setType(type);
        try{
            image1.setUrl(image.getString("url"));
            image1.setHeight(image.getInt("height"));
            image1.setHeight(image.getInt("width"));
        }catch(Exception e){e.printStackTrace();}
        return image1;
    }
}
