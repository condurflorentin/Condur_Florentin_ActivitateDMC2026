package com.example.myapplication;

import java.io.Serializable;

public class Cafea implements Serializable {

    private String nume;
    private boolean cuLapte;
    private int cantitateML;
    private TipCafea tipCafea;
    private float rating;
    private double pret;
    private boolean cuZahar;

    public Cafea(String nume, boolean cuLapte, int cantitateML,
                 TipCafea tipCafea, float rating, double pret, boolean cuZahar) {
        this.nume = nume;
        this.cuLapte = cuLapte;
        this.cantitateML = cantitateML;
        this.tipCafea = tipCafea;
        this.rating = rating;
        this.pret = pret;
        this.cuZahar = cuZahar;
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
}