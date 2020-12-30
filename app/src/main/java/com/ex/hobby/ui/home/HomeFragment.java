package com.ex.hobby.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

import com.ex.hobby.CookActivity;
import com.ex.hobby.DesignActivity;
import com.ex.hobby.DrawingActivity;
import com.ex.hobby.ExerciseActivity;
import com.ex.hobby.MainActivity;
import com.ex.hobby.MakeupActivity;
import com.ex.hobby.R;
import com.ex.hobby.TravelActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    Toolbar toolbar;
    CircleImageView cook,design, drawing,exercise,makeup,travel;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = root.findViewById(R.id.viewpager);

        // 저장해놓을 프래그먼트 개수
        viewPager.setOffscreenPageLimit(2);
        // 상태저장 기능 제거
        viewPager.setSaveEnabled(false);

        MyPagerAdapter adapter = new MyPagerAdapter(getFragmentManager());
        ViewPagerOne viewPagerOne = new ViewPagerOne();
        adapter.addItem(viewPagerOne);

        ViewPagerTwo viewPagerTwo = new ViewPagerTwo();
        adapter.addItem(viewPagerTwo);

        viewPager.setAdapter(adapter);

        CircleIndicator indicator = root.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        cook = root.findViewById(R.id.image_mcook3);
        cook.setOnClickListener(new View.OnClickListener() { //요리
            @Override
            public void onClick(View v) { //요리로 이동
                Intent intent = new Intent(getActivity(), CookActivity.class);
                startActivity(intent);
            }
        });

        design = root.findViewById(R.id.image_mdesign1);
        design.setOnClickListener(new View.OnClickListener() { //요리
            @Override
            public void onClick(View v) { //수공예로 이동
                Intent intent = new Intent(getActivity(), DesignActivity.class);
                startActivity(intent);
            }
        });

        drawing = root.findViewById(R.id.image_mdrawing2);
        drawing.setOnClickListener(new View.OnClickListener() { //요리
            @Override
            public void onClick(View v) { //미술로 이동
                Intent intent = new Intent(getActivity(), DrawingActivity.class);
                startActivity(intent);
            }
        });

        exercise = root.findViewById(R.id.image_mexercise1);
        exercise.setOnClickListener(new View.OnClickListener() { //요리
            @Override
            public void onClick(View v) { //운동으로 이동
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                startActivity(intent);
            }
        });

        makeup = root.findViewById(R.id.image_mmakeup1);
        makeup.setOnClickListener(new View.OnClickListener() { //요리
            @Override
            public void onClick(View v) { //뷰티로 이동
                Intent intent = new Intent(getActivity(), MakeupActivity.class);
                startActivity(intent);
            }
        });

        travel = root.findViewById(R.id.image_mtravel1);
        travel.setOnClickListener(new View.OnClickListener() { //요리
            @Override
            public void onClick(View v) { //여행으로 이동
                Intent intent = new Intent(getActivity(), TravelActivity.class);
                startActivity(intent);
            }
        });


        toolbar = root.findViewById(R.id.toolbar);
        MainActivity activity = (MainActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar();
        //actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        //actionBar.setDisplayHomeAsUpEnabled(true);//뒤로가기 버튼

        return root;
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

}