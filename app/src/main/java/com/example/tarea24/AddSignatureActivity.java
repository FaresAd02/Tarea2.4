package com.example.tarea24;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tarea24.DatabaseManager;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class AddSignatureActivity extends AppCompatActivity {
    private EditText descripcionEditText;
    private SignatureView signatureView;
    private Button clearButton, saveButton;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        descripcionEditText = findViewById(R.id.descripcion_edit_text);
        signatureView = findViewById(R.id.signature_view);
        clearButton = findViewById(R.id.clear_signature_button);
        saveButton = findViewById(R.id.save_button);

        dbManager = new DatabaseManager(this);

        clearButton.setOnClickListener(v -> signatureView.clear());

        saveButton.setOnClickListener(v -> {
            String description = descripcionEditText.getText().toString().trim();
            Bitmap signatureBitmap = signatureView.getSignatureBitmap();

            if (!description.isEmpty() && signatureBitmap != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] signatureBytes = bos.toByteArray();

                dbManager.addSignature(description, signatureBytes);

                Toast.makeText(this, "Signature saved successfully.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a description and sign.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
