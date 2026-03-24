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

    private Button btnAdaugaCafea;
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
                                    Toast.makeText(
                                            MainActivity.this,
                                            "Cafeaua a fost modificata",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                } else {
                                    listaCafele.add(cafea);
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
        lvCafele = findViewById(R.id.lvCafele);

        listaCafele = new ArrayList<>();
        adapter = new CafeaAdapter(this, listaCafele);
        lvCafele.setAdapter(adapter);

        btnAdaugaCafea.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCafeaActivity.class);
            launcher.launch(intent);
        });

        lvCafele.setOnItemClickListener((parent, view, position, id) -> {
            Cafea cafeaSelectata = listaCafele.get(position);

            Intent intent = new Intent(MainActivity.this, AddCafeaActivity.class);
            intent.putExtra("cafea", cafeaSelectata);
            intent.putExtra("pozitie", position);
            launcher.launch(intent);
        });

        lvCafele.setOnItemLongClickListener((parent, view, position, id) -> {
            Cafea cafeaStearsa = listaCafele.get(position);
            listaCafele.remove(position);
            adapter.notifyDataSetChanged();

            Toast.makeText(
                    MainActivity.this,
                    "S-a sters: " + cafeaStearsa.getNume(),
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });
    }
}