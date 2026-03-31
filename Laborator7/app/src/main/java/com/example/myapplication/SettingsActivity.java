package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText etDimensiune;
    private Spinner spinnerCuloare;
    private Button btnSalveazaSetari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etDimensiune = findViewById(R.id.etDimensiune);
        spinnerCuloare = findViewById(R.id.spinnerCuloare);
        btnSalveazaSetari = findViewById(R.id.btnSalveazaSetari);

        String[] culori = {"Negru", "Rosu", "Albastru", "Verde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                culori
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCuloare.setAdapter(adapter);

        incarcaSetari();

        btnSalveazaSetari.setOnClickListener(v -> salveazaSetari());
    }

    private void incarcaSetari() {
        SharedPreferences prefs = getSharedPreferences("setari_app", MODE_PRIVATE);
        float size = prefs.getFloat("textSize", 18f);
        String color = prefs.getString("textColor", "#000000");

        etDimensiune.setText(String.valueOf(size));

        switch (color) {
            case "#FF0000":
                spinnerCuloare.setSelection(1);
                break;
            case "#0000FF":
                spinnerCuloare.setSelection(2);
                break;
            case "#008000":
                spinnerCuloare.setSelection(3);
                break;
            default:
                spinnerCuloare.setSelection(0);
                break;
        }
    }

    private void salveazaSetari() {
        String dimText = etDimensiune.getText().toString().trim();
        if (dimText.isEmpty()) {
            Toast.makeText(this, "Introdu dimensiunea textului", Toast.LENGTH_SHORT).show();
            return;
        }

        float dimensiune;
        try {
            dimensiune = Float.parseFloat(dimText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dimensiune invalida", Toast.LENGTH_SHORT).show();
            return;
        }

        String culoareSelectata = spinnerCuloare.getSelectedItem().toString();
        String hexColor = "#000000";

        switch (culoareSelectata) {
            case "Rosu":
                hexColor = "#FF0000";
                break;
            case "Albastru":
                hexColor = "#0000FF";
                break;
            case "Verde":
                hexColor = "#008000";
                break;
        }

        SharedPreferences prefs = getSharedPreferences("setari_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("textSize", dimensiune);
        editor.putString("textColor", hexColor);
        editor.apply();

        Toast.makeText(this, "Setarile au fost salvate", Toast.LENGTH_SHORT).show();
        finish();
    }
}