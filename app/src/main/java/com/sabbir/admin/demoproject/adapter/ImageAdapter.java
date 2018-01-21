package com.sabbir.admin.demoproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sabbir.admin.demoproject.activities.GalleryActivity;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Admin on 1/21/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private String[] imageUrls;
    private Context mContext;
    boolean[] checkImage = new boolean[5000];

    public ImageAdapter(Context context, String[] imageUrls) {
        this.mContext = context;
        this.imageUrls = imageUrls;
        Arrays.fill(checkImage,false);
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }

        if(checkImage[i] == false) {
            Picasso.with(mContext)
                    .load(imageUrls[i])
                    .into(imageView);
            checkImage[i] = true;
        }
        return imageView;
    }


}
