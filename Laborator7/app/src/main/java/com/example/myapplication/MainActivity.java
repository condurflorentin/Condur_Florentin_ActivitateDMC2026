package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_ALL = "cafele.dat";
    private static final String FILE_FAV = "favorite.dat";

    private Button btnAdaugaCafea;
    private Button btnSetari;
    private ListView lvCafele;

    private ArrayList<Cafea> listaCafele;
    private CafeaAdapter adapter;

    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Cafea cafea = result.getData().getParcelableExtra("cafea");
                            int pozitie = result.getData().getIntExtra("pozitie", -1);

                            if (cafea != null) {
                                if (pozitie >= 0) {
                                    listaCafele.set(pozitie, cafea);
                                    FileHelper.saveList(MainActivity.this, FILE_ALL, listaCafele);

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Cafeaua a fost modificata",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                } else {
                                    listaCafele = FileHelper.readList(MainActivity.this, FILE_ALL);
                                    adapter.clear();
                                    adapter.addAll(listaCafele);
                                    adapter.notifyDataSetChanged();

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Cafeaua a fost adaugata",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAdaugaCafea = findViewById(R.id.btnAdaugaCafea);
        btnSetari = findViewById(R.id.btnSetari);
        lvCafele = findViewById(R.id.lvCafele);

        listaCafele = FileHelper.readList(this, FILE_ALL);
        adapter = new CafeaAdapter(this, listaCafele);
        lvCafele.setAdapter(adapter);

        btnAdaugaCafea.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCafeaActivity.class);
            launcher.launch(intent);
        });

        btnSetari.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        lvCafele.setOnItemClickListener((parent, view, position, id) -> {
            Cafea cafeaSelectata = listaCafele.get(position);

            Intent intent = new Intent(MainActivity.this, AddCafeaActivity.class);
            intent.putExtra("cafea", (android.os.Parcelable) cafeaSelectata);
            intent.putExtra("pozitie", position);
            launcher.launch(intent);
        });

        lvCafele.setOnItemLongClickListener((parent, view, position, id) -> {
            Cafea cafeaFavorita = listaCafele.get(position);
            FileHelper.addCafeaToFile(MainActivity.this, FILE_FAV, cafeaFavorita);

            Toast.makeText(
                    MainActivity.this,
                    "Adaugata la favorite: " + cafeaFavorita.getNume(),
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });
    }
}