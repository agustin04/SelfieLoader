package com.sergio_agustin.selfieloader;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Sergio on 23/10/2014.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
