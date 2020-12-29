package com.example.android1016.ui.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.android1016.R;
import com.example.android1016.ui.com.ComAdapter;
import com.example.android1016.ui.com.ComListItem;
import com.example.android1016.ui.com.ReviewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    TextView tv_name;
    private ListView listView;
    private ComAdapter comAdapter;
    ArrayList<String> Atitle, Aletter;
    ArrayList<String> Aimg;
    ArrayList<QueryDocumentSnapshot> queryDocumentSnapshots;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase db;

    private static final String TAG = MyFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);

        tv_name = root.findViewById(R.id.tv_name);
        listView = root.findViewById(R.id.listview);
        queryDocumentSnapshots = new ArrayList<>();

        comAdapter = new ComAdapter();

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("user").document(user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                tv_name.setText(document.get("name").toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        firebaseFirestore.collection("post").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if((user.getUid()).equals(document.get("uid"))) {
                                queryDocumentSnapshots.add(0, document);

                            }
                        gettitle(queryDocumentSnapshots);
                        }
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                ComListItem item = (ComListItem) parent.getItemAtPosition(position);
                String title = item.getTitle();
                String letter = item.getLetter();
                String img = item.getImg();

                Intent intent = new Intent(getContext(), ReviewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("letter", letter);
                intent.putExtra("img", img);
                startActivity(intent);
            }
        });

        return root;
    }
    public void gettitle(final ArrayList<QueryDocumentSnapshot> queryDocumentSnapshots) {
        Atitle = new ArrayList<>();
        Aletter = new ArrayList<>();
        Aimg = new ArrayList<>();
        comAdapter = new ComAdapter();

        //문서에서 필드 getImgUri에 해당하는 값 getimguri에 넣기
        for (int getImgCount = 0; getImgCount < queryDocumentSnapshots.size(); getImgCount++) {
            String title = queryDocumentSnapshots.get(getImgCount).get("title").toString();
            Atitle.add(title);
            String letter = queryDocumentSnapshots.get(getImgCount).get("letter").toString();
            Aletter.add(letter);
            String img = queryDocumentSnapshots.get(getImgCount).get("img").toString();
            Aimg.add(img);
        }
        for (int i = 0; i < Atitle.size(); i++) {
            comAdapter.addItem(Atitle.get(i), Aletter.get(i), Aimg.get(i));
            //Log.e("컴컴",Atitle.get(i));
            listView.setAdapter(comAdapter);
        }
    }
}