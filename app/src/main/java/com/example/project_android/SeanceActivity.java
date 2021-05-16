package com.example.project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SeanceActivity extends AppCompatActivity {


    //pour la liste des activités
    private List<Seance> seances;
    private RecyclerView mRecyclerView;
    private Recyclerview_Seance mAdapterSeance;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);


        floatingActionButton = findViewById(R.id.fab_seance);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment_seance register = Fragment_seance.newInstance("seance");
                register.show(fm, "fragment_seance");
            }
        });



        // Pour l'affichage de la liste des parcours
        mRecyclerView = findViewById(R.id.recycler_view_seance);
        mRecyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager=new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Structure structure1 = new Structure("structure_1","discipline_1","pneumonie");
        Structure structure2 = new Structure("structure_2","discipline_2","diabète");
        Structure structure3 = new Structure("structure_3","discipline_3","paralysie");
        Activity activity1 = new Activity("Titre1","bim","2 mois",R.drawable.activity);
        Activity activity2 = new Activity("Titre2","bim","3 mois",R.drawable.activity);
        Activity activity3 = new Activity("Titre3","bim","1 h",R.drawable.activity);
        seances = new ArrayList<>();
        seances.add(new Seance("le 11/02/1998","12 : 00","12 : 30",R.drawable.activity,activity1,structure1));
        seances.add(new Seance("le 16/02/1998","15 : 00","16 : 30",R.drawable.activity,activity2,structure2));
        seances.add(new Seance("le 21/02/1998","16 : 00","16 : 30",R.drawable.activity,activity3,structure3));
        
        mAdapterSeance = new Recyclerview_Seance(seances);
        mRecyclerView.setAdapter(mAdapterSeance);



    }
}