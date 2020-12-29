package com.example.android1016.ui.com;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android1016.GlideApp;
import com.example.android1016.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    TextView tv_title, tv_letter;
    ImageView iv_img;
    FirebaseStorage storage;
    StorageReference reference;
    Context context;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        context = this;
        tv_title = findViewById(R.id.tv_title);
        tv_letter = findViewById(R.id.tv_letter);
        iv_img = findViewById(R.id.iv_img);


        Intent intent = getIntent(); //보내온 intent를 얻는다

        String title = intent.getStringExtra("title");
        String letter = intent.getStringExtra("letter");
        Uri img = Uri.parse(intent.getStringExtra("img"));

        storage = FirebaseStorage.getInstance();
        reference = storage.getReferenceFromUrl("gs://poject1111.appspot.com/"+img);
        Log.e("리뷰", img.toString());
        //Uri img = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);


        tv_title.setText(title);
        tv_letter.setText(letter);
        //iv_img.setImageURI(img);
        //iv_img.setImageResource(Uri img);
        GlideApp.with(context).load(reference).into(iv_img);

    }

}
