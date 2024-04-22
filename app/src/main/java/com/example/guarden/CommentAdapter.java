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
//Adapter class that works with CommentViewHolder to create a recycler view for the list of all comments
public class CommentAdapter extends FirebaseRecyclerAdapter<Comment, CommentViewHolder> {

    public CommentAdapter(
            @NonNull FirebaseRecyclerOptions<Comment> options)
    {
        super(options);
    }
    @Override
    protected void
    onBindViewHolder(@NonNull CommentViewHolder holder,
                     int position, @NonNull Comment model)
    {
        try {
            holder.messageView.setText(model.getMessage());
            holder.nameView.setText(model.getName());
        } catch (Exception e) {
        }
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_view, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public Comment getItem(int position) {
        return super.getItem(getItemCount() - 1 - position);
    }

}
