package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    private ArrayAdapter<Cafea> adapter;

    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();

                            if (bundle != null) {
                                Cafea cafea = (Cafea) bundle.getSerializable("cafea");

                                if (cafea != null) {
                                    listaCafele.add(cafea);
                                    adapter.notifyDataSetChanged();

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Cafeaua a fost adaugata",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
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

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaCafele
        );

        lvCafele.setAdapter(adapter);

        btnAdaugaCafea.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCafeaActivity.class);
            launcher.launch(intent);
        });

        lvCafele.setOnItemClickListener((parent, view, position, id) -> {
            Cafea cafeaSelectata = listaCafele.get(position);
            Toast.makeText(
                    MainActivity.this,
                    cafeaSelectata.toString(),
                    Toast.LENGTH_SHORT
            ).show();
        });

        lvCafele.setOnItemLongClickListener((parent, view, position, id) -> {
            Cafea cafeaStearsa = listaCafele.get(position);
            listaCafele.remove(position);
            adapter.notifyDataSetChanged();

            Toast.makeText(
                    MainActivity.this,
                    "S-a sters: " + cafeaStearsa.toString(),
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });
    }
}