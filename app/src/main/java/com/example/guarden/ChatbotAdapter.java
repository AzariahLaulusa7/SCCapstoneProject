package com.example.guarden;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//Adapter class that works with ChatbotAdapter.ViewHolder to create a recycler view for the list of all chatbot messages
public class ChatbotAdapter extends RecyclerView.Adapter<ChatbotAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<String> messages;
    private DatabaseReference databaseReference;
    public ChatbotAdapter(Context context, ArrayList<String> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatbotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
        View botView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
        return new ViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String message = messages.get(position);
        holder.message.setText(message);

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.card_gchat_message_me);
        }
    }
}