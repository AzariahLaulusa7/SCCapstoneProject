package com.example.guarden;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//Adapter class that works with JournalAdapter.ViewHolder to create a recycler view for the list of all journals
public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private final Context context;
    private final RecyclerViewInterface recyclerViewInterface;
    private final ArrayList<JournalEntry> entries;
    private DatabaseReference databaseReference;
    public JournalAdapter(Context context, ArrayList<JournalEntry> entries, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.entries = entries;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public JournalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_card, parent, false);

        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);
        holder.title.setText(entry.getName());
        holder.content.setText(entry.getContent());
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            title = itemView.findViewById(R.id.journal_title);
            content = itemView.findViewById(R.id.journal_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAbsoluteAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}