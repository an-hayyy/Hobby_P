package com.ex.hobby.ui.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ex.hobby.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class ComFragment extends Fragment{
    String img;
    FloatingActionButton btn_upload;
    private ListView listView;
    private ComAdapter comAdapter;
    ArrayList<String> Atitle;
    ArrayList<String> Aletter;
    ArrayList<String> Aimg;
    ArrayList<QueryDocumentSnapshot> queryDocumentSnapshots;
    private FirebaseFirestore firebaseFirestore;
    View include;
    Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_com, container, false);

        /*include = view.findViewById(R.id.in_toolbar);
        //home = include.findViewById(R.id.home);
        toolbar = include.findViewById(R.id.toolbar);
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.back2); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
        setHasOptionsMenu(true);*/

        listView = view.findViewById(R.id.listview);
        queryDocumentSnapshots = new ArrayList<>();

        comAdapter = new ComAdapter();

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("post").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            queryDocumentSnapshots.add(0, document);
                        }
                        gettitle(queryDocumentSnapshots);
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

        btn_upload = view.findViewById(R.id.btn_upload);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UploadActivity.class);
                startActivity(intent);
            }
        });
        return view;
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
            if(queryDocumentSnapshots.get(getImgCount).get("img") != null) {
                img = queryDocumentSnapshots.get(getImgCount).get("img").toString();
                Aimg.add(img);
            }else{
                Aimg.add(null);
            }
        }
        for (int i = 0; i < Atitle.size(); i++) {
            comAdapter.addItem(Atitle.get(i), Aletter.get(i), Aimg.get(i));
            Log.e("컴컴",Atitle.get(i));
            Log.e("사진", i+1+"번쨰 :"+ Aimg.get(i));
            listView.setAdapter(comAdapter);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Intent intent = new Intent(getContext(),MainActivity.class);
                //startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { // res/menu 에서 친구 탭에서 작동 할 menu를 가져온다.
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        ((MainActivity)getActivity()).onBackPressed();
        return true;
    }*/
}