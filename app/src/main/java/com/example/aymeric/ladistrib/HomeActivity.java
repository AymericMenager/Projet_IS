package com.example.aymeric.ladistrib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private ImageView ConnectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ConnectBtn = findViewById(R.id.connect_btn);

        ConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectIntent = new Intent(HomeActivity.this, AccueilActivity.class);
                startActivity(connectIntent);
                Toast.makeText(HomeActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
