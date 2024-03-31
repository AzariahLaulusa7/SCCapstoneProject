package com.example.guarden;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder  extends RecyclerView.ViewHolder {

    ImageView imageView, tagBackgroundView;
    TextView nameView, tagView, messageView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.user_image);
        nameView = itemView.findViewById(R.id.user_id);
        messageView = itemView.findViewById(R.id.post_text);
        tagView = itemView.findViewById(R.id.forum_tag);
        tagBackgroundView = itemView.findViewById(R.id.tag_background);
    }
}
