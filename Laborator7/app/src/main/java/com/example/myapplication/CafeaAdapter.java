package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CafeaAdapter extends ArrayAdapter<Cafea> {

    private final Context context;
    private final List<Cafea> cafele;

    public CafeaAdapter(@NonNull Context context, List<Cafea> cafele) {
        super(context, 0, cafele);
        this.context = context;
        this.cafele = cafele;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cafea, parent, false);
        }

        Cafea cafea = cafele.get(position);

        TextView tvNume = convertView.findViewById(R.id.tvNumeCafea);
        TextView tvDetalii = convertView.findViewById(R.id.tvDetaliiCafea);
        TextView tvExtra = convertView.findViewById(R.id.tvExtraCafea);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        tvNume.setText(cafea.getNume() + " - " + cafea.getTipCafea());
        tvDetalii.setText("Cantitate: " + cafea.getCantitateML() + " ml | Pret: " + cafea.getPret() + " lei");
        tvExtra.setText(
                "Data: " + sdf.format(cafea.getDataPreparare()) +
                        " | Lapte: " + (cafea.isCuLapte() ? "Da" : "Nu") +
                        " | Zahar: " + (cafea.isCuZahar() ? "Da" : "Nu")
        );

        return convertView;
    }
}