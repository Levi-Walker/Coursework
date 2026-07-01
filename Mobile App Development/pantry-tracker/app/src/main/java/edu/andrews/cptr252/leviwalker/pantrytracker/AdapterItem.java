package edu.andrews.cptr252.leviwalker.pantrytracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;



public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {
    private List<InfoItem> itemList;

    AdapterItem(List<InfoItem> list) {
        itemList = list;
    }

    public void setItemList(List<InfoItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        InfoItem c = itemList.get(position);
        holder.name.setText(c.getName());
        holder.expiration.setText(c.getExpiration());
        holder.quantity.setText(c.getQuantity());

        File imgFile = new File(c.getPhoto());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.photo.setImageBitmap(bitmap);
        }
        else {
            holder.photo.setImageResource(R.drawable.apple_icon);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView expiration;
        TextView quantity;

        ItemViewHolder(View v){
            super(v);
            photo = v.findViewById(R.id.imagePhoto);
            name = v.findViewById(R.id.txtName);
            expiration = v.findViewById(R.id.txtExpire);
            quantity = v.findViewById(R.id.txtAMT);
        }
    }


}