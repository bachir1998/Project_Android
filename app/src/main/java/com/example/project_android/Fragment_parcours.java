package com.example.project_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Fragment_parcours extends DialogFragment //implements AdapterView.OnItemSelectedListener
{
    private EditText titre, description, categorie;
    private Spinner spinner_patient, spinner_intervenant;
    private Button creer;
    private FirebaseUser user;
    private DatabaseReference databaseReferenceInt;
    private DatabaseReference databaseReferenceMed;
    private String userid;
    private ArrayList<String> listIntervenant;
    private ArrayList<String> listIntkey;
    private ArrayAdapter<String> adapterInt;
    private String theKeyInt;
    private ArrayList<String> listPatient;
    private ArrayList<String> listPatkey;
    private ArrayAdapter<String> adapterPat;
    private String theKeyPat;

    private ValueEventListener listenerint;
    private ValueEventListener listenerpat;
    public Fragment_parcours() {
        // le fragment est créé par la méthode newInstance
    }


    public static Fragment_parcours newInstance(String title) {

        Fragment_parcours frag = new Fragment_parcours();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_parcours, container);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_carte_bancaire, container, false);
        super.onViewCreated(view, savedInstanceState);



        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Users").child("Medecins");
        databaseReferenceInt = FirebaseDatabase.getInstance().getReference().child("Users").child("Intervenants");
        userid = user.getUid();


        titre = view.findViewById(R.id.new_titre_parcours);
        categorie = view.findViewById(R.id.new_categorie_parcours);
        description = view.findViewById(R.id.new_description_parcours);
        spinner_intervenant = view.findViewById(R.id.choisir_intervenant);
        listIntervenant=new ArrayList<String>();
        adapterInt=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,listIntervenant);
        spinner_intervenant.setAdapter(adapterInt);
        listIntkey = new ArrayList<String>();

        spinner_patient = view.findViewById(R.id.choisir_patient);
        listPatient=new ArrayList<String>();
        adapterPat=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,listPatient);
        spinner_patient.setAdapter(adapterPat);
        listPatkey = new ArrayList<String>();

        creer = view.findViewById(R.id.btn_creer);
       // spinner_patient.setOnItemSelectedListener(this);
        //spinner_intervenant.setOnItemSelectedListener(this);

        fetchdataIntervenantAndPatient();

        spinner_intervenant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theKeyInt = listIntkey.get(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_patient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theKeyPat = listPatkey.get(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createparcours();
                Fragment_parcours.this.dismiss();
            }
        });




    }


    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/

    public void createparcours()
    {
        String title = titre.getText().toString().trim();
        String categ = categorie.getText().toString().trim();
        String descrip = description.getText().toString().trim();



        if (title.isEmpty()) {
            titre.setError("Le champ titre ne doit pas etre vide");
            titre.requestFocus();
            return;
        }
        if (categ.isEmpty()) {
            categorie.setError("Le champ categorie ne doit pas etre vide");
            categorie.requestFocus();
            return;
        }

        if (descrip.isEmpty()) {
            description.setError("Le champ description ne doit pas etre vide");
            description.requestFocus();
            return;
        }
        Parcours parcours = new Parcours(categ,descrip,theKeyInt,theKeyPat,title);
        databaseReferenceMed.child(userid).child("Parcours").setValue(parcours);
        Toast.makeText(getContext(),"Insert successfully",Toast.LENGTH_SHORT).show();
    }


    public void fetchdataIntervenantAndPatient()
    {
        listenerint=databaseReferenceInt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren()) {

                    //theKey = mydata.getKey().toString();

                    listIntervenant.add(mydata.child("prenom").getValue().toString()+ " " + mydata.child("nom").getValue().toString());
                    listIntkey.add(mydata.getKey().toString());


                }

                adapterInt.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listenerpat=databaseReferenceMed.child(userid).child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren()) {

                    //theKey = mydata.getKey().toString();

                    listPatient.add(mydata.child("prenom").getValue().toString()+ " " + mydata.child("nom").getValue().toString());
                    listPatkey.add(mydata.getKey().toString());


                }

                adapterPat.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}
