package com.example.studypartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Navigation_Drawer extends AppCompatActivity {
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    Toast.makeText(Navigation_Drawer.this, "back to Main Activity", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Navigation_Drawer.this, MainActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId()==R.id.info) {
                  startActivity(new Intent(Navigation_Drawer.this, JsonActivity.class));

                }

                    return false;
            }
        });
    }
}