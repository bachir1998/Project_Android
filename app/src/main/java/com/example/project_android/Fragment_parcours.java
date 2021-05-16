package com.example.project_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Fragment_parcours extends DialogFragment implements AdapterView.OnItemSelectedListener
{
    private EditText titre, description, categorie;
    private Spinner spinner_patient, spinner_intervenant;
    private Button creer;
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


        titre = view.findViewById(R.id.titre_parcours);
        categorie = view.findViewById(R.id.categorie_parcours);
        description = view.findViewById(R.id.description_parcours);
        spinner_patient = view.findViewById(R.id.choisir_patient);
        spinner_intervenant = view.findViewById(R.id.choisir_intervenant);
        creer = view.findViewById(R.id.btn_creer);
        spinner_patient.setOnItemSelectedListener(this);
        spinner_intervenant.setOnItemSelectedListener(this);




    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
