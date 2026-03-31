package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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

    private static final String FILE_ALL = "cafele.dat";

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
    private int pozitieEditare = -1;
    private Cafea cafeaEditata = null;

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

        aplicaSetariText();
        verificaDatePrimitePentruEditare();
    }

    private void aplicaSetariText() {
        SharedPreferences prefs = getSharedPreferences("setari_app", MODE_PRIVATE);

        float dimensiune = prefs.getFloat("textSize", 18f);
        String culoare = prefs.getString("textColor", "#000000");

        int colorInt;
        try {
            colorInt = Color.parseColor(culoare);
        } catch (Exception e) {
            colorInt = Color.BLACK;
        }

        View[] views = new View[]{
                etNume, etCantitate, etPret, etDataPreparare,
                cbLapte, swZahar, rbSpeciala
        };

        for (View view : views) {
            if (view instanceof TextView) {
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, dimensiune);
                ((TextView) view).setTextColor(colorInt);
            }
        }
    }

    private void verificaDatePrimitePentruEditare() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cafea")) {
            cafeaEditata = intent.getParcelableExtra("cafea");
            pozitieEditare = intent.getIntExtra("pozitie", -1);

            if (cafeaEditata != null) {
                etNume.setText(cafeaEditata.getNume());
                etCantitate.setText(String.valueOf(cafeaEditata.getCantitateML()));
                etPret.setText(String.valueOf(cafeaEditata.getPret()));

                calendarSelectat.setTime(cafeaEditata.getDataPreparare());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                etDataPreparare.setText(sdf.format(cafeaEditata.getDataPreparare()));

                cbLapte.setChecked(cafeaEditata.isCuLapte());
                swZahar.setChecked(cafeaEditata.isCuZahar());
                rbSpeciala.setChecked(cafeaEditata.isEsteSpeciala());
                toggleOferta.setChecked(cafeaEditata.isAreOferta());
                ratingBar.setRating(cafeaEditata.getRating());
                spinnerTip.setSelection(cafeaEditata.getTipCafea().ordinal());

                btnSalveaza.setText("Modifica");
            }
        }
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

        Cafea cafea;

        if (cafeaEditata != null) {
            cafeaEditata.setNume(nume);
            cafeaEditata.setCuLapte(cuLapte);
            cafeaEditata.setCantitateML(cantitateML);
            cafeaEditata.setTipCafea(tipCafea);
            cafeaEditata.setRating(rating);
            cafeaEditata.setPret(pret);
            cafeaEditata.setCuZahar(cuZahar);
            cafeaEditata.setDataPreparare(dataPreparare);
            cafeaEditata.setEsteSpeciala(esteSpeciala);
            cafeaEditata.setAreOferta(areOferta);

            cafea = cafeaEditata;
        } else {
            cafea = new Cafea(
                    nume,
                    cuLapte,
                    cantitateML,
                    tipCafea,
                    rating,
                    pret,
                    cuZahar,
                    dataPreparare,
                    esteSpeciala,
                    areOferta
            );

            FileHelper.addCafeaToFile(this, FILE_ALL, cafea);
        }

        Intent intent = new Intent();
        intent.putExtra("cafea", (android.os.Parcelable) cafea);
        intent.putExtra("pozitie", pozitieEditare);

        setResult(RESULT_OK, intent);
        finish();
    }
}