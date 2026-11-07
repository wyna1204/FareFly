package com.example.excessbaggagefee;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {

    private MaterialToolbar aboutToolbar;
    private TextView tvWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        aboutToolbar = findViewById(R.id.aboutToolbar);
        tvWebsite = findViewById(R.id.tvWebsite);

        aboutToolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        tvWebsite.setOnClickListener(v -> {
            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/wyna1204/FareFly")
            );

            startActivity(browserIntent);
        });
    }
}
