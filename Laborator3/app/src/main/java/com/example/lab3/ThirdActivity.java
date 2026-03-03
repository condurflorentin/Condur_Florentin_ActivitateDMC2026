package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    private int a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        String msg = "";
        if (extras != null) {
            msg = extras.getString("msg", "");
            a = extras.getInt("a", 0);
            b = extras.getInt("b", 0);
        }

        Toast.makeText(this, msg + " a=" + a + ", b=" + b, Toast.LENGTH_LONG).show();

        Button btnBack = findViewById(R.id.btnSendBack);
        btnBack.setOnClickListener(v -> {
            int sum = a + b;

            Intent result = new Intent();
            result.putExtra("msgBack", "Mesaj din ThirdActivity!");
            result.putExtra("sum", sum);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}