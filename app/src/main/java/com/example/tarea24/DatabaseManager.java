package com.example.tarea24;

import android.content.Context;
import com.example.tarea24.Signature;
import com.example.tarea24.DatabaseHelper;

import java.util.List;

public class DatabaseManager {
    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addSignature(String description, byte[] signatureImage) {
        dbHelper.aniadirFirma(new Signature(-1, description, signatureImage));
    }

    public List<Signature> getAllSignatures() {
        return dbHelper.getSignatures();
    }

    public void deleteSignature(int id) {
        dbHelper.eliminarFirma(id);
    }

}
