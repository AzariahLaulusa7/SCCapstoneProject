        package com.example.guarden;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Switch;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.ArrayList;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Pose> poseArrayList;
    private DatabaseReference databaseReference;
    public MovementAdapter(Context context, ArrayList<Pose> poseArrayList) {
        this.context = context;
        this.poseArrayList = poseArrayList;
    }
    public MovementAdapter(Context context, ArrayList<Pose> poseArrayList, DatabaseReference databaseReference) {
        this.context = context;
        this.poseArrayList=poseArrayList;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public MovementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movement_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovementAdapter.ViewHolder holder, int position) {
        Pose pose = poseArrayList.get(position);
        holder.name.setText(pose.getName());
        holder.category.setText(pose.getCategory());
        holder.thumbnail.setImageResource(pose.getImageRes());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(pose.getLike()==2)
            holder.sw.setChecked(false);
        else
            holder.sw.setChecked(true);
        holder.sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Handle switch checked
                databaseReference.child("users").child(Login.UserID).child("customPoses")
                        .child(pose.getName()).child("like").setValue(0);
            } else {
                // Handle switch unchecked
                databaseReference.child("users").child(Login.UserID).child("customPoses")
                        .child(pose.getName()).child("like").setValue(2);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("users").child(Login.UserID).child("customPoses").child(pose.getName()).setValue(null);
                        poseArrayList.remove(poseArrayList.get(position));
                        notifyItemRemoved(position);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return poseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final TextView name;
        private final TextView category;
        Switch sw;
        ImageButton edit;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            sw = itemView.findViewById(R.id.switch1);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}

