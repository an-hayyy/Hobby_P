package com.ex.hobby.ui.com;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ex.hobby.R;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {

    private Uri photouri;
    Date now = new Date();
    long time = System.currentTimeMillis();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

    final int REQUEST_CODE = 10;
    ImageView select_img;
    EditText et_title , et_letter;
    Button btn_finish;
    ImageButton btn_upload;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private static final String TAG = UploadActivity.class.getSimpleName();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        et_title = findViewById(R.id.et_title);
        et_letter = findViewById(R.id.et_letter);
        btn_upload = findViewById(R.id.btn_upload);
        select_img = findViewById(R.id.select_img);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(intent, 10);

            }
        });

        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = et_title.getText().toString(); //공백인 부분을 제거하고 보여주는 trim();
                String letter = et_letter.getText().toString();

                //맵 테이블을 파이어베이스 데이터베이스에 저장
                Map<Object, String> hashMap2 = new HashMap<>();

                hashMap2.put("title", title);
                hashMap2.put("letter", letter);
                //hashMap2.put("haha", firebaseUser.getUid()+"_"+getTime);
                hashMap2.put("img", null);
                hashMap2.put("uid", firebaseUser.getUid());
                //hashMap2.put("count", null);

                firebaseFirestore.collection("post").document(title)
                        .set(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "성공");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.e(TAG, "실패");
                    }
                });

                storeProfilePhoto(photouri);
                //Toast.makeText(UploadActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            photouri  = data.getData();
        }
        select_img.setImageURI(photouri);
    }

    //사진 Storage에 등록 / DB 업데이트
    public void storeProfilePhoto(Uri photouri){
        // Storage에 프로필 사진 등록
        storageRef = storage.getReferenceFromUrl("gs://poject1111.appspot.com/")
                .child("photo/" + firebaseUser.getUid()+"_"+ formatter.format(now) + ".png");

        storageRef.putFile(photouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "후기 등록 성공", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(getApplicationContext(), "후기 등록 실패", Toast.LENGTH_SHORT).show();
            }
        });

        // DB에 프로필 사진 추가하여 저장
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String title = et_title.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("post").document(title);
        docRef.update("img" ,"photo/" + firebaseUser.getUid() + "_" + formatter.format(now) + ".png")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "DB 변경 성공" );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "DB 변경 실패" );
                    }
                });

    }
}
