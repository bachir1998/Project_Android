package com.example.project_android;

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

public class Recyclerview_Parcours extends RecyclerView.Adapter<Recyclerview_Parcours.ConteneurDeDonnee>
{
    private List<Parcours> parcours;

    public Recyclerview_Parcours(List<Parcours> parcours) {
        this.parcours = parcours;
    }

    @NonNull
    @Override
    public Recyclerview_Parcours.ConteneurDeDonnee onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_parcours, parent, false);
        return new ConteneurDeDonnee(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConteneurDeDonnee holder, int position) {
        holder.titre.setText(parcours.get(position).getTitre());
        holder.description.setText(parcours.get(position).getDescription());
        holder.categorie.setText(parcours.get(position).getCategorie());
        holder.imageView.setImageResource(parcours.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return parcours.size();
    }

    public static class ConteneurDeDonnee extends RecyclerView.ViewHolder{
        TextView titre;
        TextView description;
        TextView categorie;
        ImageView imageView;
        Button btn;

        public ConteneurDeDonnee(View itemView) {
            super(itemView);
            titre= (TextView) itemView.findViewById(R.id.titre_parcours);
            description= (TextView) itemView.findViewById(R.id.description_parcours);
            categorie= (TextView) itemView.findViewById(R.id.categorie_parcours);
            imageView =  itemView.findViewById(R.id.img_parcours);
            btn = itemView.findViewById(R.id.button_detail);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),EspaceActivity.class);
                    view.getContext().startActivity(intent);

                }
            });




        }


    }

}
