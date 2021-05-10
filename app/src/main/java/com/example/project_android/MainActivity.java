package com.example.project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Fragment_Register_Listener, Fragment_Login_Listener {

    public  Button inscription_med;
    public  Button inscription_pat;
    public  Button inscription_int;
    public  Button connexion_med;
    public  Button connexion_pat;
    public  Button connexion_int;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inscription_med = findViewById(R.id.sincrire_med);
        inscription_pat = findViewById(R.id.sincrire_patient);
        inscription_int = findViewById(R.id.sincrire_intervenant);
        connexion_med = findViewById(R.id.se_connecter_med);
        connexion_pat = findViewById(R.id.seconnecter_patient);
        connexion_int = findViewById(R.id.seconnecter_intervenant);

        inscription_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRegisterDialog();
                GlobalVariables.persRoleInscrip=0;


            }
        });

        inscription_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowRegisterDialog();
                GlobalVariables.persRoleInscrip=1;

            }
        });



        inscription_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRegisterDialog();
                GlobalVariables.persRoleInscrip=2;
            }
        });


       connexion_med.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ShowLoginDialog();
               GlobalVariables.persRoleConnect =0;

           }
       });

       connexion_int.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ShowLoginDialog();
               GlobalVariables.persRoleConnect = 1;
           }
       });

       connexion_pat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ShowLoginDialog();
               GlobalVariables.persRoleConnect = 2;
           }
       });


    }

    public void ShowRegisterDialog() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment_Register register = Fragment_Register.newInstance("Docteur");
        register.show(fm, "Fragment_Register");

    }

    public void ShowLoginDialog() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment_Login login = Fragment_Login.newInstance("Docteur");
        login.show(fm, "Fragment_Login");

    }


    public void onOkClickEmptyDialog() {
        Toast.makeText(this, "Veuillez remplir tous les champs svp ", Toast.LENGTH_SHORT).show();
    }


}