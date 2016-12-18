package com.example.jenis.tabbedactivityapp;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Jenis on 8/18/2016.
 */
public class GliderStuff {

    public static void profileImage(String url, ImageView imageView){
        Context context = imageView.getContext();
        Glide.with(context).load(url).placeholder(R.drawable.pic).into(imageView);
    }
}
