package com.example.tarea24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView signaturesRecyclerView;
    private SignaturesAdapter signaturesAdapter;
    private FloatingActionButton fabAddSignature;
    private static final int REQUEST_ADD_SIGNATURE = 1;
    DatabaseManager dbManager = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signaturesRecyclerView = findViewById(R.id.signatures_recycler_view);
        signaturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseManager dbManager = new DatabaseManager(this);
        List<Signature> signatures = dbManager.getAllSignatures();

        signaturesAdapter = new SignaturesAdapter(signatures);
        signaturesRecyclerView.setAdapter(signaturesAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_signature);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddSignatureActivity.class);
            startActivityForResult(intent, REQUEST_ADD_SIGNATURE);
        });
    }

    private void getSignaturesFromDatabase() {
        List<Signature> updatedSignatures = dbManager.getAllSignatures();
        signaturesAdapter.updateSignatures(updatedSignatures);
        signaturesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSignaturesFromDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_SIGNATURE) {
            if (resultCode == RESULT_OK) {

                getSignaturesFromDatabase();
            }
        }
    }
}
