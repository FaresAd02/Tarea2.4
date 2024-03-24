package com.example.tarea24;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SignaturesAdapter extends RecyclerView.Adapter<SignaturesAdapter.ViewHolder> {

    private List<Signature> signaturesList;

    public SignaturesAdapter(List<Signature> signatures) {
        this.signaturesList = signatures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_signature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Signature signature = signaturesList.get(position);
        holder.signatureDescription.setText(signature.getDescripcion());

        byte[] firmaBytes = signature.getFirmaDigital();
        if (firmaBytes != null) {
            Bitmap signatureBitmap = BitmapFactory.decodeByteArray(firmaBytes, 0, firmaBytes.length);
            holder.signatureImage.setImageBitmap(signatureBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return signaturesList.size();
    }

    public void updateSignatures(List<Signature> newSignatures) {
        signaturesList.clear();
        signaturesList.addAll(newSignatures);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView signatureImage;
        public TextView signatureDescription;

        public ViewHolder(View view) {
            super(view);
            signatureImage = view.findViewById(R.id.signature_image);
            signatureDescription = view.findViewById(R.id.signature_description);
        }
    }
}
