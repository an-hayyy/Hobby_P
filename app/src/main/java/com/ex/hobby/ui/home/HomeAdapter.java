package com.ex.hobby.ui.home;

import android.content.Context;
import android.view.View;

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
