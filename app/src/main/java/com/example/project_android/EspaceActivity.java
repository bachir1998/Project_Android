package com.example.project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EspaceActivity extends AppCompatActivity {

    //pour la liste des activités
    private List<Activity> activity;
    private RecyclerView mRecyclerView;
    private Recyclerview_Activity mAdapterActivity;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_activity);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment_activity register = Fragment_activity.newInstance("activity");
                register.show(fm, "fragment_activity");
            }
        });

        // Pour l'affichage de la liste des parcours
        mRecyclerView = findViewById(R.id.recycler_view_activity);
        mRecyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager=new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        activity = new ArrayList<>();
        activity.add(new Activity("Titre1","bim","30 mn",R.drawable.activity));
        activity.add(new Activity("Titre2","bim","30 mn",R.drawable.activity));
        activity.add(new Activity("Titre3","bim","1 h",R.drawable.activity));



        mAdapterActivity = new Recyclerview_Activity(activity);
        mRecyclerView.setAdapter(mAdapterActivity);
    }
}