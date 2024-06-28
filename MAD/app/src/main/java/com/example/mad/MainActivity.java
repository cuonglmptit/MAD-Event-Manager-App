package com.example.mad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mad.adapter.AdapterViewPager;
import com.example.mad.fragment.FragmentHome;
import com.example.mad.fragment.FragmentManage;
import com.example.mad.fragment.FragmentUser;
import com.example.mad.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ViewPager2 pagerMain;
    private BottomNavigationView bottomNav;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogin();

        setContentView(R.layout.activity_main);

        FirebaseMessaging fcm = FirebaseMessaging.getInstance();
        fcm.setAutoInitEnabled(true);

        initInstances();
        setupViewPager();
        setupBottomNav();
    }

    private void checkLogin() {
        sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        System.out.println("Logged:" + isLoggedIn);

        // Nếu chưa đăng nhập, chuyển sang Activity đăng nhập
        if (!isLoggedIn) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để ngăn người dùng quay lại
            return;
        }

    }


    private void initInstances() {
        pagerMain = findViewById(R.id.pagerMain);
        bottomNav = findViewById(R.id.bottomNav);
    }
    private void setupViewPager() {
        fragmentArrayList.add(new FragmentHome());
        fragmentArrayList.add(new FragmentManage());
        fragmentArrayList.add(new FragmentUser());

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
        pagerMain.setAdapter(adapterViewPager);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNav.setSelectedItemId(R.id.itNavHome);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.itNavManage);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.itNavUser);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

    private void setupBottomNav() {

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itNavHome:
                        pagerMain.setCurrentItem(0);
                        break;
                    case R.id.itNavManage:
                        pagerMain.setCurrentItem(1);
                        break;
                    case R.id.itNavUser:
                        pagerMain.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
        MenuItem menuItem = bottomNav.getMenu().findItem(R.id.itNavUser);
        if (menuItem != null) {
            menuItem.setTitle(sharedPreferences.getString("name", ""));
        }
    }

}