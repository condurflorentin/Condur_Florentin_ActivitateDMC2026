package com.example.myapplication;

import android.app.DatePickerDialog;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCafeaActivity extends AppCompatActivity {

    private EditText etNume;
    private EditText etCantitate;
    private EditText etPret;
    private EditText etDataPreparare;
    private CheckBox cbLapte;
    private Switch swZahar;
    private RadioButton rbSpeciala;
    private Spinner spinnerTip;
    private RatingBar ratingBar;
    private ToggleButton toggleOferta;
    private Button btnSalveaza;

    private Calendar calendarSelectat;

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
        etDataPreparare = findViewById(R.id.etDataPreparare);
        cbLapte = findViewById(R.id.cbLapte);
        swZahar = findViewById(R.id.swZahar);
        rbSpeciala = findViewById(R.id.rbSpeciala);
        spinnerTip = findViewById(R.id.spinnerTip);
        ratingBar = findViewById(R.id.ratingBar);
        toggleOferta = findViewById(R.id.toggleOferta);
        btnSalveaza = findViewById(R.id.btnSalveaza);

        calendarSelectat = Calendar.getInstance();

        ArrayAdapter<TipCafea> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TipCafea.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTip.setAdapter(adapter);

        etDataPreparare.setOnClickListener(v -> deschideCalendar());
        btnSalveaza.setOnClickListener(v -> salveazaCafea());
    }

    private void deschideCalendar() {
        int an = calendarSelectat.get(Calendar.YEAR);
        int luna = calendarSelectat.get(Calendar.MONTH);
        int zi = calendarSelectat.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendarSelectat.set(Calendar.YEAR, year);
                    calendarSelectat.set(Calendar.MONTH, month);
                    calendarSelectat.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    etDataPreparare.setText(sdf.format(calendarSelectat.getTime()));
                },
                an,
                luna,
                zi
        );

        dialog.show();
    }

    private void salveazaCafea() {
        String nume = etNume.getText().toString().trim();
        String cantitateText = etCantitate.getText().toString().trim();
        String pretText = etPret.getText().toString().trim();
        String dataText = etDataPreparare.getText().toString().trim();

        if (nume.isEmpty() || cantitateText.isEmpty() || pretText.isEmpty() || dataText.isEmpty()) {
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
        Date dataPreparare = calendarSelectat.getTime();

        Cafea cafea = new Cafea(
                nume,
                cuLapte,
                cantitateML,
                tipCafea,
                rating,
                pret,
                cuZahar,
                dataPreparare
        );

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cafea", cafea);
        bundle.putBoolean("esteSpeciala", esteSpeciala);
        bundle.putBoolean("areOferta", areOferta);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();
    }
}