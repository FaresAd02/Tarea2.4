package com.example.tarea24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FIRMAS.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "firmas";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESC = "descripcion";
    private static final String COLUMN_FIRMA = "firma";

    private static final String TABLE_CREATE =
            "CREATE TABLE FIRMAS (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DESC + " TEXT, " +
                    COLUMN_FIRMA + " BLOB)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void aniadirFirma(Signature signature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESC, signature.getDescripcion());
        values.put(COLUMN_FIRMA, signature.getFirmaDigital());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Signature> getSignatures() {
        List<Signature> signatures = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("FIRMAS", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
                byte[] signatureBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("firma"));

                Bitmap signatureImage = BitmapFactory.decodeByteArray(signatureBytes, 0, signatureBytes.length);

                Signature signature = new Signature(id, description, signatureBytes);
                signatures.add(signature);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return signatures;
    }

    public int actualizarFirma(Signature signature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESC, signature.getDescripcion());
        values.put(COLUMN_FIRMA, signature.getFirmaDigital());

        int updatedRows = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(signature.getId())});
        db.close();
        return updatedRows;
    }

    public void eliminarFirma(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
