package com.nanodegree.udacy.backingapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nanodegree.udacy.backingapp.R;

/**
 * Created by root on 26/09/17.
 */

public class utils {
    // this method help to download image with glide library
    public static void downloadImage(Context c, int url, ImageView imageView) {
        Glide.with(c)
                .load(url)
                .placeholder(R.drawable.ic_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    public static void downloadImage(Context c, String url, ImageView imageView) {
        Glide.with(c).load(url).
                placeholder(R.drawable.ic_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

//    public static String getImageUrl(ArrayList<Etablissement> objetList, int position) {
//
//        Log.e(" getImageUrl", objetList.get(position).getAvatar());
//        return IMAGE_URL + objetList.get(position).getAvatar();
//    }
//
//    public static String getImageUrl(Etablissement objet) {
//        return IMAGE_URL + objet.getAvatar();
//    }

}
