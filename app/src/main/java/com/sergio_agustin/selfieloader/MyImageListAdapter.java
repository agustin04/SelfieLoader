package com.sergio_agustin.selfieloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sergio on 23/10/2014.
 */
public class MyImageListAdapter extends BaseAdapter {

    ArrayList<InstagramData> myList = new ArrayList<InstagramData>();
    private final Context context;
    private LayoutInflater inflater;


    private LruCache<String, Bitmap> mMemoryCache;

    public MyImageListAdapter(Context context, ArrayList<InstagramData> list) {
        this.myList = list;
        this.context = context;

        inflater = LayoutInflater.from(this.context);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }


    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public InstagramData getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageHolder holder;
        ImageView img = null;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.single_image_item, parent, false);
            holder = new ImageHolder();
            img = (ImageView) convertView.findViewById(R.id.icon);
            holder.myImage = img;
            convertView.setTag(holder);
        }
        else
        {
            holder = (ImageHolder) convertView.getTag();
            //img = holder.myImage;
        }

        if(myList != null || myList.size() !=0){

            //Aplpying patter
            int result = (position+2) % 3;

            String imageURL;
            int height;
            int width;
            if(result == 2){
                imageURL = myList.get(position).getStdRes().getUrl();
                height = myList.get(position).getStdRes().getHeight();
                width = myList.get(position).getStdRes().getWidth();

            }else{
                imageURL = myList.get(position).getLowRes().getUrl();
                height = myList.get(position).getLowRes().getHeight();
                width = myList.get(position).getLowRes().getWidth();
            }


            holder.myImage.setMaxHeight(height);
            holder.myImage.setMaxWidth(width);
            if(getBitmapFromMemCache(imageURL) != null){
                Log.i("INSTAGRAM","Loading IMAGE from CACHE in position:"+position);
                holder.myImage.setImageBitmap(getBitmapFromMemCache(imageURL));
            }else{

                DisplayImageOptions  options_image = new DisplayImageOptions.Builder().showImageOnLoading(R.color.BLACK).showImageForEmptyUri(R.color.WHITE).showImageOnFail(R.color.WHITE)
                        .cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                        .bitmapConfig(Bitmap.Config.RGB_565).build();

                ImageLoader.getInstance().displayImage(imageURL, holder.myImage, options_image, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.i("INSTAGRAM","OnLOadingComplete imageURI"+imageUri);
                        addBitmapToMemoryCache(imageUri,loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });


            }

        }


        return convertView;
    }

    private class ImageHolder {
        public ImageView myImage;

    }

}


