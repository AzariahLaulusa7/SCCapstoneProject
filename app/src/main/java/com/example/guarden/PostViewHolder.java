package com.example.guarden;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//ViewHolder class associated with PostAdapter
public class PostViewHolder  extends RecyclerView.ViewHolder {

    ImageView imageView, tagBackgroundView;
    TextView nameView, tagView, messageView;

    public PostViewHolder(@NonNull View postView) {
        super(postView);
        imageView = postView.findViewById(R.id.user_image);
        nameView = postView.findViewById(R.id.user_id);
        messageView = postView.findViewById(R.id.post_text);
        tagView = postView.findViewById(R.id.forum_tag);
        tagBackgroundView = postView.findViewById(R.id.tag_background);
    }
}
