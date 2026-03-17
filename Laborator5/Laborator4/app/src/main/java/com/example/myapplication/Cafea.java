package com.example.myapplication;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Cafea implements Serializable {

    private String nume;
    private boolean cuLapte;
    private int cantitateML;
    private TipCafea tipCafea;
    private float rating;
    private double pret;
    private boolean cuZahar;
    private Date dataPreparare;

    public Cafea(String nume, boolean cuLapte, int cantitateML,
                 TipCafea tipCafea, float rating, double pret,
                 boolean cuZahar, Date dataPreparare) {
        this.nume = nume;
        this.cuLapte = cuLapte;
        this.cantitateML = cantitateML;
        this.tipCafea = tipCafea;
        this.rating = rating;
        this.pret = pret;
        this.cuZahar = cuZahar;
        this.dataPreparare = dataPreparare;
    }

    public String getNume() {
        return nume;
    }

    public boolean isCuLapte() {
        return cuLapte;
    }

    public int getCantitateML() {
        return cantitateML;
    }

    public TipCafea getTipCafea() {
        return tipCafea;
    }

    public float getRating() {
        return rating;
    }

    public double getPret() {
        return pret;
    }

    public boolean isCuZahar() {
        return cuZahar;
    }

    public Date getDataPreparare() {
        return dataPreparare;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return nume + "|" + cantitateML + "ml|" + pret + " lei|" + tipCafea + "|" + sdf.format(dataPreparare);
    }
}