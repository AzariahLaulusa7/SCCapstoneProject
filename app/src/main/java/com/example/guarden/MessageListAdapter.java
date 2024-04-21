package com.example.guarden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static com.example.guarden.Message.LayoutOne;
import static com.example.guarden.Message.LayoutTwo;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> messages;
    private Context context;

    public MessageListAdapter(ArrayList<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return message.getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Message.LayoutOne:
                View layoutOne = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
                return new LayoutOneViewHolder(layoutOne);
            case Message.LayoutTwo:
                View layoutTwo = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message, parent, false);
                return new LayoutTwoViewHolder(layoutTwo);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        switch (holder.getItemViewType()) {
            case Message.LayoutOne:
                ((LayoutOneViewHolder) holder).setMessage(message.getMessage());
                break;
            case Message.LayoutTwo:
                ((LayoutTwoViewHolder) holder).setMessage(message.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class LayoutOneViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        LayoutOneViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.userMessage);
        }

        void setMessage(String text) {
            message.setText(text);
        }
    }

    static class LayoutTwoViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        LayoutTwoViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.botMessage);
        }

        void setMessage(String text) {
            message.setText(text);
        }
    }
}
