package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cafea implements Parcelable, Serializable {

    //private static final long serialVersionUID = 1L;

    private String nume;
    private boolean cuLapte;
    private int cantitateML;
    private TipCafea tipCafea;
    private float rating;
    private double pret;
    private boolean cuZahar;
    private Date dataPreparare;
    private boolean esteSpeciala;
    private boolean areOferta;

    public Cafea(String nume, boolean cuLapte, int cantitateML,
                 TipCafea tipCafea, float rating, double pret,
                 boolean cuZahar, Date dataPreparare,
                 boolean esteSpeciala, boolean areOferta) {
        this.nume = nume;
        this.cuLapte = cuLapte;
        this.cantitateML = cantitateML;
        this.tipCafea = tipCafea;
        this.rating = rating;
        this.pret = pret;
        this.cuZahar = cuZahar;
        this.dataPreparare = dataPreparare;
        this.esteSpeciala = esteSpeciala;
        this.areOferta = areOferta;
    }

    protected Cafea(Parcel in) {
        nume = in.readString();
        cuLapte = in.readByte() != 0;
        cantitateML = in.readInt();
        tipCafea = TipCafea.valueOf(in.readString());
        rating = in.readFloat();
        pret = in.readDouble();
        cuZahar = in.readByte() != 0;
        long dataMillis = in.readLong();
        dataPreparare = new Date(dataMillis);
        esteSpeciala = in.readByte() != 0;
        areOferta = in.readByte() != 0;
    }

    public static final Creator<Cafea> CREATOR = new Creator<Cafea>() {
        @Override
        public Cafea createFromParcel(Parcel in) {
            return new Cafea(in);
        }

        @Override
        public Cafea[] newArray(int size) {
            return new Cafea[size];
        }
    };

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

    public boolean isEsteSpeciala() {
        return esteSpeciala;
    }

    public boolean isAreOferta() {
        return areOferta;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setCuLapte(boolean cuLapte) {
        this.cuLapte = cuLapte;
    }

    public void setCantitateML(int cantitateML) {
        this.cantitateML = cantitateML;
    }

    public void setTipCafea(TipCafea tipCafea) {
        this.tipCafea = tipCafea;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public void setCuZahar(boolean cuZahar) {
        this.cuZahar = cuZahar;
    }

    public void setDataPreparare(Date dataPreparare) {
        this.dataPreparare = dataPreparare;
    }

    public void setEsteSpeciala(boolean esteSpeciala) {
        this.esteSpeciala = esteSpeciala;
    }

    public void setAreOferta(boolean areOferta) {
        this.areOferta = areOferta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeByte((byte) (cuLapte ? 1 : 0));
        dest.writeInt(cantitateML);
        dest.writeString(tipCafea.name());
        dest.writeFloat(rating);
        dest.writeDouble(pret);
        dest.writeByte((byte) (cuZahar ? 1 : 0));
        dest.writeLong(dataPreparare.getTime());
        dest.writeByte((byte) (esteSpeciala ? 1 : 0));
        dest.writeByte((byte) (areOferta ? 1 : 0));
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return nume + " | " + cantitateML + " ml | " + pret + " lei | " + tipCafea +
                " | " + sdf.format(dataPreparare);
    }
}