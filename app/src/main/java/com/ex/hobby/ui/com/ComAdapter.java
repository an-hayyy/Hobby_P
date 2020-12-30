package com.ex.hobby.ui.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ex.hobby.GlideApp;
import com.ex.hobby.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ComAdapter extends BaseAdapter {

    //Context mContext = null;
    ArrayList<ComListItem> comListItems = new ArrayList<ComListItem>();
    FirebaseStorage storage;
    StorageReference reference;
    //LayoutInflater inf;

    public ComAdapter() {
        //this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return comListItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return comListItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_com_listitem, parent, false);
        }
        ComListItem item = comListItems.get(pos);

        storage = FirebaseStorage.getInstance();
        reference = storage.getReferenceFromUrl("gs://poject1111.appspot.com/" + item.getImg());

        TextView title = convertView.findViewById(R.id.tv_title);
        ImageView img = convertView.findViewById(R.id.iv_img);
        TextView letter = convertView.findViewById(R.id.tv_letter);

        title.setText(item.getTitle());
        letter.setText(item.getLetter());
        GlideApp.with(context).load(reference).into(img);

        return convertView;
    }

    public void addItem(String title, String letter, String img){
        ComListItem comListItem = new ComListItem();

        comListItem.setTitle(title);
        comListItem.setLetter(letter);
        comListItem.setImg(img);

        comListItems.add(comListItem);
    }
}