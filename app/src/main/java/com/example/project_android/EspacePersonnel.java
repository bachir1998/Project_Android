package com.example.project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EspacePersonnel extends AppCompatActivity {


    //Pour le drawer
    private TextView nom_prenom;
    private TextView recup_email;
    private ImageView imageProfil;
    private NavigationView navigationView;

    private FirebaseUser user;
    private DatabaseReference databaseReferenceMed,databaseReferenceInt,databaseReferencePat;
    private String userid;
    public String usermed;


    //pour la liste des parcours
    private List<Parcours> parcours;
    private RecyclerView mRecyclerView;
    private Recyclerview_Parcours mAdapterParcours;
    private RecyclerView.LayoutManager mLayoutManager;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_personnel);


        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Users").child("Medecins");
        databaseReferenceInt = FirebaseDatabase.getInstance().getReference().child("Users").child("Intervenants");
        databaseReferencePat = FirebaseDatabase.getInstance().getReference().child("Users").child("Medecins").child("Patients");
        userid = user.getUid();




        //Complément du drawer
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.ouvrir,R.string.fermer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.view_navigation);
        final View headerLayout = navigationView.getHeaderView(0);
        nom_prenom = (TextView) headerLayout.findViewById(R.id.nom_prenom);
        recup_email = (TextView) headerLayout.findViewById(R.id.recup_email_pers);
        imageProfil = (ImageView) headerLayout.findViewById(R.id.imageProfil);


        //Pour l'affichage de la liste des parcours
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager=new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        parcours = new ArrayList<>();




        databaseReferenceMed.child(userid).child("Parcours").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                     parcours.add(new Parcours(snapshot.child("categorie").getValue().toString(),
                             snapshot.child("description").getValue().toString(),
                             snapshot.child("intervenant").getValue().toString(),
                             snapshot.child("patient").getValue().toString(),
                             snapshot.child("titre").getValue().toString()));


                }

                mAdapterParcours = new Recyclerview_Parcours(parcours);
                mRecyclerView.setAdapter(mAdapterParcours);

               // mAdapterParcours.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

     /*
        Patient patient1 = new Patient("Gueye","Bachir","basse","gueyebachir98@gmail.com","patient");
        Intervenant intervenant1 = new Intervenant("Flora","Diane","basse","gueyebachir98@gmail.com","intervenant");
        Patient patient2 = new Patient("Sidibé","Kadiatou","basse","gueyebachir98@gmail.com","patient");
        Intervenant intervenant2 = new Intervenant("Haidara","Fatimetou","basse","gueyebachir98@gmail.com","intervenant");
        Patient patient3 = new Patient("Allegou","Bachi","basse","gueyebachir98@gmail.com","patient");
        Intervenant intervenant3 = new Intervenant("Aghillas","Aimad","basse","gueyebachir98@gmail.com","intervenant");
        parcours.add(new Parcours("Parcours1","bim","bam",R.drawable.parcours,patient1,intervenant1));
        parcours.add(new Parcours("Parcours2","bim","bam",R.drawable.parcours,patient2,intervenant2));
        parcours.add(new Parcours("Parcours3","bim","bam",R.drawable.parcours,patient3,intervenant3));


       */


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.parcours)
                {
                    if(GlobalVariables.persRoleInscrip==0  || GlobalVariables.persRoleConnect==0 )
                    {
                        FragmentManager fm = getSupportFragmentManager();
                        Fragment_parcours register = Fragment_parcours.newInstance("parcours");
                        register.show(fm, "fragment_parcours");
                    }
                }

                if(id == R.id.seance)
                {
                  PageSeance();

                }

                if(id==R.id.logout)
                {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(EspacePersonnel.this,MainActivity.class));
                    finish();
                   // PageAccueil();
                }
                return true;
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drawermenu, menu);
        menu = navigationView.getMenu();
        MenuItem item_parcours = menu.findItem(R.id.parcours);
        MenuItem item_seance = menu.findItem(R.id.seance);
        MenuItem item_statistique = menu.findItem(R.id.statistic);
        MenuItem item_logout = menu.findItem(R.id.logout);

        if(GlobalVariables.persRoleInscrip==0 || GlobalVariables.persRoleConnect==0)
        {
            item_parcours.setVisible(true);
            item_parcours.setTitle("Définir parcours");
            item_seance.setVisible(false);
            item_statistique.setVisible(true);
            item_logout.setVisible(true);
           // nom_prenom.setText(GlobalVariables.prenom+" "+GlobalVariables.nom);
            //recup_email.setText(GlobalVariables.email);
            imageProfil.setImageResource(R.drawable.doctor);
            databaseReferenceMed.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Personne personne = snapshot.getValue(Personne.class);

                    if(personne!=null)
                    {
                        String prenom = personne.prenom;
                        String nom = personne.nom;
                        String email = personne.email;

                        nom_prenom.setText(prenom+" "+nom);
                        recup_email.setText(email);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(EspacePersonnel.this,"Something wrong happened",Toast.LENGTH_LONG).show();

                }
            });


        }
        if(GlobalVariables.persRoleInscrip==2 ||  GlobalVariables.persRoleConnect==2)
        {
            item_parcours.setVisible(false);
            item_parcours.setTitle("Mon parcours");
            item_seance.setVisible(false);
            item_statistique.setVisible(true);
            item_logout.setVisible(true);
            imageProfil.setImageResource(R.drawable.patient);
            databaseReferencePat.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Personne personne = snapshot.getValue(Personne.class);

                    if(personne!=null)
                    {
                        String prenom = personne.prenom;
                        String nom = personne.nom;
                        String email = personne.email;

                        nom_prenom.setText(prenom+" "+nom);
                        recup_email.setText(email);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(EspacePersonnel.this,"Something wrong happened",Toast.LENGTH_LONG).show();

                }
            });

            parcours.add(new Parcours("categorie","description1","intervenant1","patient1","titre1"));
            parcours.add(new Parcours("categorie","description1","intervenant1","patient1","titre1"));
            mAdapterParcours = new Recyclerview_Parcours(parcours);
            mRecyclerView.setAdapter(mAdapterParcours);

        }
        if(GlobalVariables.persRoleInscrip==1 ||  GlobalVariables.persRoleConnect==1)
        {
            item_parcours.setVisible(false);
            //item_parcours.setTitle("Définir parcours");
            item_seance.setVisible(true);
            item_statistique.setVisible(true);
            item_logout.setVisible(true);
            imageProfil.setImageResource(R.drawable.intervenant);
            databaseReferenceInt.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Personne personne = snapshot.getValue(Personne.class);

                    if(personne!=null)
                    {
                        String prenom = personne.prenom;
                        String nom = personne.nom;
                        String email = personne.email;

                        nom_prenom.setText(prenom+" "+nom);
                        recup_email.setText(email);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(EspacePersonnel.this,"Something wrong happened",Toast.LENGTH_LONG).show();

                }
            });

            parcours.add(new Parcours("categorie","description1","intervenant1","patient1","titre1"));
            parcours.add(new Parcours("categorie","description1","intervenant1","patient1","titre1"));
            mAdapterParcours = new Recyclerview_Parcours(parcours);
            mRecyclerView.setAdapter(mAdapterParcours);

        }



        return true;
    }

    public void PageSeance()
    {
        Intent intent = new Intent(this,SeanceActivity.class);
        startActivity(intent);
    }

    public void PageAccueil()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }





}