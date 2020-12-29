package com.example.android1016.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android1016.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class HomeAdapter extends PagerAdapter {

    private Context mContext = null ;

    public HomeAdapter(Context context){
        mContext = context;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
