package com.example.excessbaggagefee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {

    private MaterialToolbar aboutToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        aboutToolbar = findViewById(R.id.aboutToolbar);

        aboutToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
