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
//Adapter class that works with MovementViewList to create a recycler view for the list of all posts
//Creates ViewHolder objects as needed to display posts
public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostViewHolder> {

    public PostAdapter(
            @NonNull FirebaseRecyclerOptions<Post> options)
    {
        super(options);
    }
    @Override
    protected void
    onBindViewHolder(@NonNull PostViewHolder holder,
                     int position, @NonNull Post model)
    {
        try {
            holder.nameView.setText(model.getName());
            holder.messageView.setText(model.getMessage());
            holder.tagView.setText(model.getTag());
            holder.imageView.setImageResource(model.getImage());
            holder.tagBackgroundView.setImageResource(model.getTagBackground());
        } catch (Exception e) {
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            //intent.putExtra("model", model);
            intent.putExtra("latitude", model.getName());
            intent.putExtra("longitude", model.getMessage());
            intent.putExtra("pic", model.getImage());
            intent.putExtra("postKey", model.getKey());

            holder.itemView.getContext().startActivity(intent);
        });
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_view, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public Post getItem(int position) {
        return super.getItem(getItemCount() - 1 - position);
    }

}