package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private static final int REQ_THIRD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn = findViewById(R.id.btnOpenThird);

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("msg", "Salut din SecondActivity!");
            bundle.putInt("a", 10);
            bundle.putInt("b", 5);

            intent.putExtras(bundle);

            startActivityForResult(intent, REQ_THIRD);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_THIRD && resultCode == RESULT_OK && data != null) {
            String msgBack = data.getStringExtra("msgBack");
            int sum = data.getIntExtra("sum", 0);

            android.widget.Toast.makeText(this,
                    msgBack + " Suma = " + sum,
                    android.widget.Toast.LENGTH_LONG).show();
        }
    }
}