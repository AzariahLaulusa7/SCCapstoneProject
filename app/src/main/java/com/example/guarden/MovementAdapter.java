        package com.example.guarden;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.ArrayList;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Pose> poseArrayList;
    public MovementAdapter(Context context, ArrayList<Pose> poseArrayList) {
        this.context = context;
        this.poseArrayList = poseArrayList;
    }

    @NonNull
    @Override
    public MovementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movement_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovementAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Pose pose = poseArrayList.get(position);
        holder.name.setText(pose.getName());
        holder.category.setText(pose.getCategory());
        holder.thumbnail.setImageResource(pose.getImageRes());
        holder.description.setText(pose.getDescription());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return poseArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final TextView name;
        private final TextView category;
        private final TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            description = itemView.findViewById(R.id.description);
        }
    }
}

