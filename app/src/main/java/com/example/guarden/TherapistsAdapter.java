package com.example.guarden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

//Adapter class that is associated with the therapist list. Combines ViewHolder and adapter into one class
public class TherapistsAdapter extends RecyclerView.Adapter<TherapistsAdapter.ViewHolder> {

    private List<Therapist> therapists; //Creates array list to hold therapists
    private Context context;

    public TherapistsAdapter(List<Therapist> therapists, Context context) {
        this.therapists = therapists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.therapist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Gets therapist from list and sets each attribute
        Therapist therapist = therapists.get(position);
        holder.tvName.setText(therapist.getName());
        holder.tvSpecialty.setText(therapist.getSpecialty());
        holder.tvAddress.setText(therapist.getAddress());
        holder.tvPhone.setText(therapist.getPhone());
        holder.tvEmail.setText(therapist.getEmail());
    }

    @Override
    public int getItemCount() {
        return therapists.size();
    }
    //ViewHolder class that sets each card in the therapist list
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvSpecialty, tvAddress, tvPhone, tvEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSpecialty = itemView.findViewById(R.id.tvSpecialty);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
