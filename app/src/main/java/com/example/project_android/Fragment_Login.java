package com.example.project_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class Fragment_Login extends DialogFragment
{
    private EditText connect_pers_email, connect_pers_mdp, connect_pers_specialite;
    private ImageView imageView;
    private Button login;
    private ProgressDialog progressDialogloading;
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        fragment_login_listener = (Fragment_Login_Listener) getActivity();
        connect_pers_email= (EditText) view.findViewById(R.id.connect_pers_email);
        connect_pers_mdp= (EditText) view.findViewById(R.id.connect_pers_mdp);
        connect_pers_specialite= (EditText) view.findViewById(R.id.connect_pers_specialite);
        imageView = view.findViewById(R.id.image_connect);
        progressDialogloading = new ProgressDialog(getContext());


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

               LoginUser();

           }
       });



    }

    public void LoginUser() {


        String email = connect_pers_email.getText().toString().trim();
        String password = connect_pers_mdp.getText().toString().trim();
        String specialite = connect_pers_specialite.getText().toString().trim();

        if(email.isEmpty())
        {
            connect_pers_email.setError("Le champ email ne doit pas etre vide");
            connect_pers_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            connect_pers_email.setError("Veuillez renseigner une adresse email valide");
            connect_pers_email.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            connect_pers_mdp.setError("Le champ password ne doit pas etre vide");
            connect_pers_mdp.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            connect_pers_mdp.setError("Le champ password ne doit contenir minimum 6 caractères");
            connect_pers_mdp.requestFocus();
            return;

        }

        progressDialogloading.setTitle("Authentification");
        progressDialogloading.setMessage("Veuillez patienter, connexion en cours");
        progressDialogloading.setCanceledOnTouchOutside(true);
        progressDialogloading.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    SendCurrentUserToEspacePersonnelActivity();


                   /* if(user.isEmailVerified())
                    {


                    }
                    else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(getContext(),"Email ou mot de passe incorrect",Toast.LENGTH_SHORT);
                    }*/

                }
                else
                {
                    connect_pers_email.setError("Le champ email ou mot de passe saisi est incorrect ");
                    //String message = task.getException().toString();
                    Toast.makeText(getContext(),"Oups Erreur de connexion, veuillez réessayer :",Toast.LENGTH_SHORT);
                    progressDialogloading.dismiss();

                }

            }
        });








        /*

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

         */


    }

    private void SendCurrentUserToEspacePersonnelActivity() {
        Intent pagemainIntent = new Intent(getContext(),EspacePersonnel.class);
        pagemainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pagemainIntent);
        Fragment_Login.this.dismiss();
    }






}
