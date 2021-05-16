package com.example.project_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class Fragment_Register extends DialogFragment implements AdapterView.OnItemSelectedListener
{
    private EditText prenom, nom, email,password,specialite,age;
    private TextView  textchoice;
    private ImageView imageView;
    private Button register;
    Fragment_Register_Listener fragment_register_listener;
    private Spinner spinner;

    public Fragment_Register() {
        // le fragment est créé par la méthode newInstance
    }


    public static Fragment_Register newInstance(String title) {

        Fragment_Register frag = new Fragment_Register();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_register, container);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_carte_bancaire, container, false);
        super.onViewCreated(view, savedInstanceState);

       // MainActivity mainActivity = (MainActivity) getActivity();
       fragment_register_listener = (Fragment_Register_Listener) getActivity();
        email= (EditText) view.findViewById(R.id.email_pers);
        password= (EditText) view.findViewById(R.id.mdp_pers);
        specialite= (EditText) view.findViewById(R.id.specialite);
        imageView = view.findViewById(R.id.imagepers);
        register = view.findViewById(R.id.register_pers);
        prenom = view.findViewById(R.id.prenom_pers);
        nom = view.findViewById(R.id.nom_pers);

        spinner = view.findViewById(R.id.choice_patient);
        textchoice = view.findViewById(R.id.textchoice);


        if(GlobalVariables.persRoleInscrip == 0)
        {

            textchoice.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            specialite.setText("Medecin");
            imageView.setImageResource(R.drawable.doctor);

        }

        if(GlobalVariables.persRoleInscrip==2)
        {

            textchoice.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            specialite.setText("Patient");
            imageView.setImageResource(R.drawable.patient);
            spinner.setOnItemSelectedListener(this);


        }
        if(GlobalVariables.persRoleInscrip==1)
        {

            textchoice.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            specialite.setText("Intervenant");
            imageView.setImageResource(R.drawable.intervenant);


        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables.specialite = specialite.getText().toString();
                GlobalVariables.prenom = prenom.getText().toString();
                GlobalVariables.nom = nom.getText().toString();
                GlobalVariables.email = email.getText().toString();
                GlobalVariables.password =password.getText().toString();
                if(!GlobalVariables.specialite.isEmpty() && !GlobalVariables.prenom.isEmpty() && !GlobalVariables.nom.isEmpty()
                        && !GlobalVariables.email.isEmpty() && !GlobalVariables.password.isEmpty())
                {

                    Intent intent = new Intent(getContext(), EspacePersonnel.class);
                    startActivity(intent);
                    Fragment_Register.this.dismiss();

                }
                else
                {
                    fragment_register_listener.onOkClickEmptyDialog();
                }





            }
        });









    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
