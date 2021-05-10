package com.example.project_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class Fragment_Login extends DialogFragment
{
    private EditText connect_pers_email, connect_pers_mdp, connect_pers_specialite;
    private ImageView imageView;
    private Button login;

    Fragment_Login_Listener fragment_login_listener;

    public Fragment_Login() {
        // le fragment est créé par la méthode newInstance
    }


    public static Fragment_Login newInstance(String title) {

        Fragment_Login frag = new Fragment_Login();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_connexion, container);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_carte_bancaire, container, false);
        super.onViewCreated(view, savedInstanceState);

       //MainActivity mainActivity = (MainActivity) getActivity();
        fragment_login_listener = (Fragment_Login_Listener) getActivity();
        connect_pers_email= (EditText) view.findViewById(R.id.connect_pers_email);
        connect_pers_mdp= (EditText) view.findViewById(R.id.connect_pers_mdp);
        connect_pers_specialite= (EditText) view.findViewById(R.id.connect_pers_specialite);
        imageView = view.findViewById(R.id.image_connect);


        if(GlobalVariables.persRoleConnect == 0)
        {
            connect_pers_specialite.setText("Medecin");
            imageView.setImageResource(R.drawable.doctor);

        }

        if(GlobalVariables.persRoleConnect==2)
        {
            connect_pers_specialite.setText("Patient");
            imageView.setImageResource(R.drawable.patient);


        }
        if(GlobalVariables.persRoleConnect==1)
        {
            connect_pers_specialite.setText("Intervenant");
            imageView.setImageResource(R.drawable.intervenant);


        }
        login = view.findViewById(R.id.btn_connect);

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               GlobalVariables.specialite_connect = connect_pers_specialite.getText().toString();
               GlobalVariables.email_connect = connect_pers_email.getText().toString();
               GlobalVariables.password_connect =connect_pers_mdp.getText().toString();
               if(!GlobalVariables.specialite_connect.isEmpty() && !GlobalVariables.email_connect.isEmpty()
                       && !GlobalVariables.password_connect.isEmpty())
               {

                   Intent intent = new Intent(getContext(), EspacePersonnel.class);
                   startActivity(intent);
                   Fragment_Login.this.dismiss();

               }
               else
               {
                   fragment_login_listener.onOkClickEmptyDialog();
               }

           }
       });



    }





}
