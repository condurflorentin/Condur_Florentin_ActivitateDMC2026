package com.example.myapplication;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {

    public static void saveList(Context context, String fileName, ArrayList<Cafea> lista) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lista);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cafea> readList(Context context, String fileName) {
        ArrayList<Cafea> lista = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            lista = (ArrayList<Cafea>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static void addCafeaToFile(Context context, String fileName, Cafea cafea) {
        ArrayList<Cafea> lista = readList(context, fileName);
        lista.add(cafea);
        saveList(context, fileName, lista);
    }
}