package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAdaugaCafea;
    private TextView tvNume;
    private TextView tvCantitate;
    private TextView tvPret;
    private TextView tvLapte;
    private TextView tvZahar;
    private TextView tvTip;
    private TextView tvRating;
    private TextView tvSpeciala;
    private TextView tvOferta;

    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            Intent data = result.getData();

                            Cafea cafea = (Cafea) data.getSerializableExtra("cafea");
                            boolean speciala = data.getBooleanExtra("esteSpeciala", false);
                            boolean oferta = data.getBooleanExtra("areOferta", false);

                            if (cafea != null) {

                                tvNume.setText("Nume: " + cafea.getNume());
                                tvCantitate.setText("Cantitate: " + cafea.getCantitateML() + " ml");
                                tvPret.setText("Pret: " + cafea.getPret() + " lei");
                                tvLapte.setText("Cu lapte: " + (cafea.isCuLapte() ? "Da" : "Nu"));
                                tvZahar.setText("Cu zahar: " + (cafea.isCuZahar() ? "Da" : "Nu"));
                                tvTip.setText("Tip cafea: " + cafea.getTipCafea());
                                tvRating.setText("Rating: " + cafea.getRating());
                                tvSpeciala.setText("Cafea speciala: " + (speciala ? "Da" : "Nu"));
                                tvOferta.setText("Oferta: " + (oferta ? "Da" : "Nu"));

                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAdaugaCafea = findViewById(R.id.btnAdaugaCafea);

        tvNume = findViewById(R.id.tvNume);
        tvCantitate = findViewById(R.id.tvCantitate);
        tvPret = findViewById(R.id.tvPret);
        tvLapte = findViewById(R.id.tvLapte);
        tvZahar = findViewById(R.id.tvZahar);
        tvTip = findViewById(R.id.tvTip);
        tvRating = findViewById(R.id.tvRating);
        tvSpeciala = findViewById(R.id.tvSpeciala);
        tvOferta = findViewById(R.id.tvOferta);

        btnAdaugaCafea.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, AddCafeaActivity.class);

            launcher.launch(intent);

        });
    }
}