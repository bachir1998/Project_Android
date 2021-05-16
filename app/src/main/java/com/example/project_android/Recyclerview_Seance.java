package com.example.project_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Recyclerview_Seance extends RecyclerView.Adapter<Recyclerview_Seance.ConteneurDeDonnee>
{
    private List<Seance> seance;

    public Recyclerview_Seance(List<Seance> seance) {
        this.seance = seance;
    }

    @NonNull
    @Override
    public Recyclerview_Seance.ConteneurDeDonnee onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_seance, parent, false);
        return new Recyclerview_Seance.ConteneurDeDonnee(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConteneurDeDonnee holder, int position) {
        holder.date.setText(seance.get(position).getDate());
        holder.heure_debut.setText(seance.get(position).getHeure_debut());
        holder.heure_fin.setText(seance.get(position).getHeure_fin());
        holder.activity.setText(seance.get(position).getActivity().getTitre());
        holder.structure.setText(seance.get(position).getStructure().getNom_structure());
        holder.imageView.setImageResource(seance.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return seance.size();
    }

    public static class ConteneurDeDonnee extends RecyclerView.ViewHolder{
        TextView date;
        TextView heure_debut;
        TextView heure_fin;
        TextView activity;
        TextView structure;
        ImageView imageView;


        public ConteneurDeDonnee(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.date_seance);
            heure_debut= (TextView) itemView.findViewById(R.id.heuredeb_seance);
            heure_fin= (TextView) itemView.findViewById(R.id.heurefin_seance);
            activity = itemView.findViewById(R.id.nom_activite);
            structure= itemView.findViewById(R.id.nom_structure);
            imageView =  itemView.findViewById(R.id.img_seance);



        }


    }
}
