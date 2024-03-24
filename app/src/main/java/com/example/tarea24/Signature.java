package com.example.tarea24;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Signature {
    private int id;
    private String descripcion;
    private byte[] firmaDigital;
    public Signature(int id, String descripcion, byte[] firmaDigital) {
        this.id = id;
        this.descripcion = descripcion;
        this.firmaDigital = firmaDigital;
    }

    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public byte[] getFirmaDigital() { return firmaDigital; }

    public void setId(int id) { this.id = id; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFirmaDigital(byte[] firmaDigital) { this.firmaDigital = firmaDigital; }

    public static byte[] convertBitmapToByteArray(Bitmap signatureImage) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        signatureImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] firmaDigital) {
        return BitmapFactory.decodeByteArray(firmaDigital, 0, firmaDigital.length);
    }

    public Bitmap getSignatureImage() {
        return convertByteArrayToBitmap(this.firmaDigital);
    }

    public void setSignatureImage(Bitmap signatureImage) {
        this.firmaDigital = convertBitmapToByteArray(signatureImage);
    }
}
