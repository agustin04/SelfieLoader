package com.sergio_agustin.selfieloader;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class  ImageListActivity extends Activity {

ArrayList<InstagramData> myList = new ArrayList<InstagramData>();
        ListView listView;
        SelfieLoader selfieLoader = new SelfieLoader();
        MyImageListAdapter adapter;
        boolean isLoading = false;

public enum DownloadType{
    FIRST_TIME,
    ONCE_AGAIN
}

Image.ImageType currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        currentType = Image.ImageType.STD_RES;

        adapter = new MyImageListAdapter(ImageListActivity.this, myList);
        listView =  (ListView)findViewById(R.id.imageList);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                Log.i("INSTAGRAM","OnScrollStateChanged state:"+i);
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItems, int totalItems) {
                int position = firstVisibleItem+visibleItems;
                int limit = totalItems;

                // Check if bottom has been reached
                if (position >= limit && totalItems > 0 && !isLoading) {
                    Log.i("INSTAGRAM","IM GETTING TO THE BOTTOM OF THE LIST, first:"+firstVisibleItem+", visibles:"+visibleItems+", totalItems:"+totalItems);
                    if(isNetworkAvailable())
                        new DownloadSelfiesClass().execute(DownloadType.ONCE_AGAIN);
                    else
                        Toast.makeText(getApplicationContext(),R.string.no_internet,Toast.LENGTH_LONG).show();
                }
            }
        });
        if(isNetworkAvailable())
            new DownloadSelfiesClass().execute(DownloadType.FIRST_TIME);
        else
            Toast.makeText(getApplicationContext(),R.string.no_internet,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRestart(){
        super.onRestart();

        if(isNetworkAvailable())
            new DownloadSelfiesClass().execute(DownloadType.FIRST_TIME);
        else
            Toast.makeText(getApplicationContext(),R.string.no_internet,Toast.LENGTH_LONG).show();

    }


private class DownloadSelfiesClass extends AsyncTask<DownloadType,Void, Void>{

    @Override
    protected void onPreExecute(){
        Log.i("INSTAGRAM","DoenloadSelfies - isLoading TRUE");
        isLoading = true;
    }

    @Override
    protected Void doInBackground(DownloadType... downloadTypes) {
        DownloadType downloadType = downloadTypes[0];

        if(downloadType == DownloadType.FIRST_TIME){
            selfieLoader.retrieveFirstTimeSelfieData();
        }else{
            selfieLoader.retrieveMoreSelfieData();
        }
        Log.i("INSTAGRAM", "DownloadSelfies - retrieveSelfie Data, myList size:"+myList.size());
        //myList = selfieLoader.getSelfieImages(myList, Image.ImageType.STD_RES);
        myList.addAll(selfieLoader.getInstagramData());
        Log.i("INSTAGRAM", "DownloadSelfies - Selfie Data gotten, myList size:"+myList.size());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        Log.i("INSTAGRAM","DoenloadSelfies - isLoading FALSE");
        isLoading = false;
        adapter.notifyDataSetChanged();

    }
}

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
