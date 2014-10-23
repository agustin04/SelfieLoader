package com.sergio_agustin.selfieloader;

import java.util.ArrayList;

/**
 * Created by Sergio on 23/10/2014.
 */
public class InstagramObject {

    private String paginationUrl;
    private int metaCode;
    private ArrayList<InstagramData> data;

    public InstagramObject(){
        paginationUrl = null;
        data = null;
    }

    public InstagramObject(ArrayList<InstagramData> data, int metaCode, String paginationUrl) {
        this.data = data;
        this.metaCode = metaCode;
        this.paginationUrl = paginationUrl;
    }

    public String getPaginationUrl() {
        return paginationUrl;
    }

    public void setPaginationUrl(String paginationUrl) {
        this.paginationUrl = paginationUrl;
    }

    public int getMetaCode() {
        return metaCode;
    }

    public void setMetaCode(int metaCode) {
        this.metaCode = metaCode;
    }

    public ArrayList<InstagramData> getData() {
        return data;
    }

    public void setData(ArrayList<InstagramData> data) {
        this.data = data;
    }
}
