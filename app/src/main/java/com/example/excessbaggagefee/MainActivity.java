package com.example.excessbaggagefee;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etRate;
    private Spinner spTicketClass;
    private Button btnCalculate;

    private TextView tvExcessWeight;
    private TextView tvExcessCharge;
    private TextView tvTotal;

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(
                android.graphics.Color.parseColor("#6A2C91")
        );

        setContentView(R.layout.activity_main);

        // Connect UI components
        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);

        spTicketClass = findViewById(R.id.spTicketClass);
        btnCalculate = findViewById(R.id.btnCalculate);

        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotal = findViewById(R.id.tvTotal);

        // Navigation Drawer
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.navView);

        // Open drawer
        toolbar.setNavigationOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_share) {
                Toast.makeText(this,
                        "Share link will be available after GitHub upload",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Drawer menu selection
        navView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                drawerLayout.closeDrawer(GravityCompat.START);

            } else if (id == R.id.nav_about) {

                drawerLayout.closeDrawer(GravityCompat.START);

                Intent intent = new Intent(
                        MainActivity.this,
                        AboutActivity.class
                );

                startActivity(intent);
            }

            return true;
        });

        // Spinner Adapter
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter<CharSequence>(
                        this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.ticket_classes)
                ) {
                    @Override
                    public View getView(int position, View convertView,
                                        android.view.ViewGroup parent) {

                        TextView textView = (TextView) super.getView(
                                position, convertView, parent);

                        textView.setTextColor(
                                android.graphics.Color.parseColor("#49345B"));

                        textView.setTextSize(14);
                        return textView;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                android.view.ViewGroup parent) {

                        TextView textView = (TextView) super.getDropDownView(
                                position, convertView, parent);

                        textView.setTextColor(
                                android.graphics.Color.parseColor("#49345B"));

                        textView.setBackgroundColor(
                                android.graphics.Color.WHITE);

                        return textView;
                    }
                };

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spTicketClass.setAdapter(adapter);

        // Default baggage rate
        spTicketClass.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        if (position == 0) {
                            etRate.setText("15");
                        } else {
                            etRate.setText("12");
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });

        // Calculate button
        btnCalculate.setOnClickListener(
                v -> calculateFee()
        );
    }

    // Calculate excess baggage fee
    private void calculateFee() {

        String weightInput =
                etWeight.getText().toString().trim();

        String rateInput =
                etRate.getText().toString().trim();

        // Check empty fields
        if (weightInput.isEmpty() || rateInput.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        try {

            double weight = Double.parseDouble(weightInput);
            double rate = Double.parseDouble(rateInput);

            // Check invalid values
            if (!Double.isFinite(weight) ||
                    !Double.isFinite(rate) ||
                    weight < 0 || rate < 0) {

                Toast.makeText(
                        this,
                        "Please enter valid positive numbers",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Determine baggage allowance
            double allowance;

            if (spTicketClass.getSelectedItemPosition() == 0) {
                allowance = 20;
            } else {
                allowance = 40;
            }

            // Calculate excess baggage
            double excessWeight =
                    Math.max(0, weight - allowance);

            double excessCharge =
                    excessWeight * rate;

            // Calculate 8% service tax
            double total =
                    excessCharge * 1.08;

            // Display results
            tvExcessWeight.setText(
                    String.format(
                            Locale.US,
                            "Excess Baggage Weight: %.2f kg",
                            excessWeight
                    )
            );

            tvExcessCharge.setText(
                    String.format(
                            Locale.US,
                            "Excess Baggage Charge: RM %.2f",
                            excessCharge
                    )
            );

            tvTotal.setText(
                    String.format(
                            Locale.US,
                            "RM %.2f",
                            total
                    )
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter valid numbers",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
