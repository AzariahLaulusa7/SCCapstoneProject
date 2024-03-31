package com.example.guarden;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<Item, MyViewHolder> {

    public MyAdapter(
            @NonNull FirebaseRecyclerOptions<Item> options)
    {
        super(options);
    }
    @Override
    protected void
    onBindViewHolder(@NonNull MyViewHolder holder,
                     int position, @NonNull Item model)
    {
        holder.nameView.setText(model.getName());
        holder.messageView.setText(model.getMessage());
        holder.tagView.setText(model.getTag());
        holder.imageView.setImageResource(model.getImage());
        holder.tagBackgroundView.setImageResource(model.getTagBackground());
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

}
