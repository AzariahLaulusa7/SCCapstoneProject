package com.example.guarden;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//ViewHolder Class associated with CommentAdapter class
public class CommentViewHolder  extends RecyclerView.ViewHolder {

    TextView messageView, nameView;

    public CommentViewHolder(@NonNull View commentView) {
        super(commentView);
        messageView = commentView.findViewById(R.id.text);
        nameView = commentView.findViewById(R.id.comment_name);
    }
}