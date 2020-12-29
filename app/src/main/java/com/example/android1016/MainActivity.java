package com.example.android1016;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.android1016.ui.com.ComFragment;
import com.example.android1016.ui.home.HomeFragment;
import com.example.android1016.ui.my.MyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    //View include;
    //Toolbar toolbar;

    HomeFragment fragment_home;
    ComFragment fragment_com;
    MyFragment fragment_my;

    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //include = findViewById(R.id.in_toolbar);
        //home = include.findViewById(R.id.home);
        /*toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        navView = findViewById(R.id.nav_view);
        fragment_home = new HomeFragment();
        fragment_com = new ComFragment();
        fragment_my = new MyFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_home).commitAllowingStateLoss();

        //bottom
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_home, getString(R.string.fragment_home)).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_com:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_com, getString(R.string.fragment_com)).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_my:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_my, getString(R.string.fragment_my)).commitAllowingStateLoss();
                        return true;

                    default:
                        return false;
                }
            }
        });
        //actionBar.setHomeAsUpIndicator(R.drawable.button_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
