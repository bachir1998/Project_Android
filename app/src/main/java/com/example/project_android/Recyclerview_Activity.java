package com.example.project_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Recyclerview_Activity extends RecyclerView.Adapter<Recyclerview_Activity.ConteneurDeDonnee>
{

    private List<Activity> activity;
    public Context context;

    public Recyclerview_Activity(List<Activity> activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public Recyclerview_Activity.ConteneurDeDonnee onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_activity, parent, false);
        return new Recyclerview_Activity.ConteneurDeDonnee(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConteneurDeDonnee holder, int position) {
        holder.titre.setText(activity.get(position).getTitre());
        holder.description.setText(activity.get(position).getDescription());
        holder.duree.setText(activity.get(position).getDuree());
        holder.imageView.setImageResource(activity.get(position).getImage());
       /* holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EspaceActivity.class);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return activity.size();
    }

    public static class ConteneurDeDonnee extends RecyclerView.ViewHolder{
        TextView titre;
        TextView description;
        TextView duree;
        ImageView imageView;


        public ConteneurDeDonnee(View itemView) {
            super(itemView);
            titre= (TextView) itemView.findViewById(R.id.titre_activity);
            description= (TextView) itemView.findViewById(R.id.description_activity);
            duree= (TextView) itemView.findViewById(R.id.duree_activity);
            imageView =  itemView.findViewById(R.id.img_activity);



        }


    }
}
