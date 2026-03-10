package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCafeaActivity extends AppCompatActivity {
    private EditText etNume;
    private EditText etCantitate;
    private EditText etPret;
    private CheckBox cbLapte;
    private Switch swZahar;
    private RadioButton rbSpeciala;
    private Spinner spinnerTip;
    private RatingBar ratingBar;
    private ToggleButton toggleOferta;
    private Button btnSalveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_cafea);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNume = findViewById(R.id.etNume);
        etCantitate = findViewById(R.id.etCantitate);
        etPret = findViewById(R.id.etPret);
        cbLapte = findViewById(R.id.cbLapte);
        swZahar = findViewById(R.id.swZahar);
        rbSpeciala = findViewById(R.id.rbSpeciala);
        spinnerTip = findViewById(R.id.spinnerTip);
        ratingBar = findViewById(R.id.ratingBar);
        toggleOferta = findViewById(R.id.toggleOferta);
        btnSalveaza = findViewById(R.id.btnSalveaza);

        ArrayAdapter<TipCafea> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TipCafea.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTip.setAdapter(adapter);

        btnSalveaza.setOnClickListener(v -> salveazaCafea());
    }

    private void salveazaCafea() {

        String nume = etNume.getText().toString().trim();
        String cantitateText = etCantitate.getText().toString().trim();
        String pretText = etPret.getText().toString().trim();

        if (nume.isEmpty() || cantitateText.isEmpty() || pretText.isEmpty()) {
            Toast.makeText(this, "Completeaza toate campurile", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantitateML;
        double pret;

        try {
            cantitateML = Integer.parseInt(cantitateText);
            pret = Double.parseDouble(pretText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Introdu valori numerice valide", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean cuLapte = cbLapte.isChecked();
        boolean cuZahar = swZahar.isChecked();
        boolean esteSpeciala = rbSpeciala.isChecked();
        boolean areOferta = toggleOferta.isChecked();

        float rating = ratingBar.getRating();

        TipCafea tipCafea = (TipCafea) spinnerTip.getSelectedItem();

        Cafea cafea = new Cafea(
                nume,
                cuLapte,
                cantitateML,
                tipCafea,
                rating,
                pret,
                cuZahar
        );

        Intent intent = new Intent();
        intent.putExtra("cafea", cafea);
        intent.putExtra("esteSpeciala", esteSpeciala);
        intent.putExtra("areOferta", areOferta);

        setResult(RESULT_OK, intent);
        finish();
    }
}