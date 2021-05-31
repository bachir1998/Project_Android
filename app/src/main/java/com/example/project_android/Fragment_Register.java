package com.example.project_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class Fragment_Register extends DialogFragment
{
    private FirebaseAuth mAuth;
    public ProgressDialog progressDialogloading;
    private EditText editTextprenom, editTextnom, editTexemail,editTextpassword,editTextspecialite;
    private TextView  textchoice;
    private ImageView imageView;
    private Button register_med, register_int, register_pat;
    Fragment_Register_Listener fragment_register_listener;
    private Spinner spinner;
    private  Personne personne;
    public DatabaseReference databaseReference;
    private ValueEventListener listener;
    private ArrayList<String> list;
    private ArrayList<String> listkey;
    private ArrayAdapter<String> adapter;
    private String theKey;





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
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Medecins");
       // MainActivity mainActivity = (MainActivity) getActivity();
        fragment_register_listener = (Fragment_Register_Listener) getActivity();
        editTexemail= (EditText) view.findViewById(R.id.email_pers);
        editTextpassword= (EditText) view.findViewById(R.id.mdp_pers);
        editTextspecialite= (EditText) view.findViewById(R.id.specialite);
        imageView = view.findViewById(R.id.imagepers);
        register_med = view.findViewById(R.id.register_pers);
        register_int = view.findViewById(R.id.register_pers_int);
        register_pat = view.findViewById(R.id.register_pers_pat);

        editTextprenom = view.findViewById(R.id.prenom_pers);
        editTextnom = view.findViewById(R.id.nom_pers);
         progressDialogloading = new ProgressDialog(getContext());

        spinner = view.findViewById(R.id.choice_patient);
        textchoice = view.findViewById(R.id.textchoice);
        list=new ArrayList<String>();
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        listkey = new ArrayList<String>();

        if(GlobalVariables.persRoleInscrip == 0)
        {

            textchoice.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            editTextspecialite.setText("Medecin");
            imageView.setImageResource(R.drawable.doctor);
            register_med.setVisibility(View.VISIBLE);

        }

        if(GlobalVariables.persRoleInscrip==2)
        {
            fetchdata();

            textchoice.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            editTextspecialite.setText("Patient");
            imageView.setImageResource(R.drawable.patient);
            register_pat.setVisibility(View.VISIBLE);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    theKey = listkey.get(i).toString();
                    GlobalVariables.uidMed = theKey;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
           // spinner.setOnItemSelectedListener(this);



        }
        if(GlobalVariables.persRoleInscrip==1)
        {

            textchoice.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            editTextspecialite.setText("Intervenant");
            imageView.setImageResource(R.drawable.intervenant);
            register_int.setVisibility(View.VISIBLE);



        }



        register_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    RegisterUserMed();

            }
        });

        register_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterUserInt();

            }
        });

        register_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterUserPat();

                //Toast.makeText(getContext(),theKey,Toast.LENGTH_SHORT).show();

            }
        });









    }

/* implements AdapterView.OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
*/

    public void RegisterUserMed() {
        String email = editTexemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String prenom = editTextprenom.getText().toString().trim();
        String nom = editTextnom.getText().toString().trim();
        String specialite = editTextspecialite.getText().toString().trim();



        if (email.isEmpty()) {
            editTexemail.setError("Le champ email ne doit pas etre vide");
            editTexemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTexemail.setError("Veuillez renseigner une adresse email valide");
            editTexemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Le champ password ne doit pas etre vide");
            editTextpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Le champ password ne doit contenir minimum 6 caractères");
            editTextpassword.requestFocus();
            return;

        }

        if (prenom.isEmpty()) {
            editTextprenom.setError("Le champ prenom ne doit pas etre vide");
            editTextprenom.requestFocus();
            return;
        }
        if (nom.isEmpty()) {
            editTextnom.setError("Le champ nom ne doit pas etre vide");
            editTextnom.requestFocus();
            return;
        }

        progressDialogloading.setTitle("Création de nouveau compte Médecin");
            progressDialogloading.setMessage("Veuillez patienter, Votre compte est en cours de création");
            progressDialogloading.setCanceledOnTouchOutside(true);
            progressDialogloading.show();


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        personne = new Personne(email, password, prenom, nom, specialite);


                        FirebaseDatabase.getInstance().getReference().child("Users").child("Medecins")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(personne).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    SendCurrentUserToEspacePersonnel();
                                    Toast.makeText(getContext(), "Compte créer avec succés", Toast.LENGTH_SHORT).show();
                                    progressDialogloading.dismiss();

                                } else {
                                    String message = task.getException().toString();
                                    Toast.makeText(getContext(), "Erreur lors de l'inscription, veuillez réessayer :" + message, Toast.LENGTH_SHORT);
                                    progressDialogloading.dismiss();


                                }
                            }
                        });


                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(getContext(), "Erreur lors de l'inscription :" + message, Toast.LENGTH_SHORT);
                        progressDialogloading.dismiss();

                    }

                }
            });


    }

    public void RegisterUserInt() {

        String email = editTexemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String prenom = editTextprenom.getText().toString().trim();
        String nom = editTextnom.getText().toString().trim();
        String specialite = editTextspecialite.getText().toString().trim();


        if (email.isEmpty()) {
            editTexemail.setError("Le champ email ne doit pas etre vide");
            editTexemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTexemail.setError("Veuillez renseigner une adresse email valide");
            editTexemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Le champ password ne doit pas etre vide");
            editTextpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Le champ password ne doit contenir minimum 6 caractères");
            editTextpassword.requestFocus();
            return;

        }

        if (prenom.isEmpty()) {
            editTextprenom.setError("Le champ prenom ne doit pas etre vide");
            editTextprenom.requestFocus();
            return;
        }
        if (nom.isEmpty()) {
            editTextnom.setError("Le champ nom ne doit pas etre vide");
            editTextnom.requestFocus();
            return;
        }

        progressDialogloading.setTitle("Création de nouveau compte Intervenant");
        progressDialogloading.setMessage("Veuillez patienter, Votre compte est en cours de création");
        progressDialogloading.setCanceledOnTouchOutside(true);
        progressDialogloading.show();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    personne = new Personne(email, password, prenom, nom, specialite);

                    FirebaseDatabase.getInstance().getReference().child("Users").child("Intervenants")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(personne).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                SendCurrentUserToEspacePersonnel();
                                Toast.makeText(getContext(), "Compte créer avec succés", Toast.LENGTH_SHORT).show();
                                progressDialogloading.dismiss();

                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(getContext(), "Erreur lors de l'inscription, veuillez réessayer :" + message, Toast.LENGTH_SHORT);
                                progressDialogloading.dismiss();


                            }
                        }
                    });


                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getContext(), "Erreur lors de l'inscription :" + message, Toast.LENGTH_SHORT);
                    progressDialogloading.dismiss();

                }

            }
        });


    }

    public void RegisterUserPat() {

        String email = editTexemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String prenom = editTextprenom.getText().toString().trim();
        String nom = editTextnom.getText().toString().trim();
        String specialite = editTextspecialite.getText().toString().trim();


        if (email.isEmpty()) {
            editTexemail.setError("Le champ email ne doit pas etre vide");
            editTexemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTexemail.setError("Veuillez renseigner une adresse email valide");
            editTexemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Le champ password ne doit pas etre vide");
            editTextpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("Le champ password ne doit contenir minimum 6 caractères");
            editTextpassword.requestFocus();
            return;

        }

        if (prenom.isEmpty()) {
            editTextprenom.setError("Le champ prenom ne doit pas etre vide");
            editTextprenom.requestFocus();
            return;
        }
        if (nom.isEmpty()) {
            editTextnom.setError("Le champ nom ne doit pas etre vide");
            editTextnom.requestFocus();
            return;
        }

        progressDialogloading.setTitle("Création de nouveau compte Patient");
        progressDialogloading.setMessage("Veuillez patienter, Votre compte est en cours de création");
        progressDialogloading.setCanceledOnTouchOutside(true);
        progressDialogloading.show();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    personne = new Personne(email, password, prenom, nom, specialite);

                    FirebaseDatabase.getInstance().getReference().child("Users").child("Medecins").child(theKey).child("Patients")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(personne).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                SendCurrentUserToEspacePersonnel();
                                Toast.makeText(getContext(), "Compte créer avec succés", Toast.LENGTH_SHORT).show();
                                progressDialogloading.dismiss();

                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(getContext(), "Erreur lors de l'inscription, veuillez réessayer :" + message, Toast.LENGTH_SHORT);
                                progressDialogloading.dismiss();


                            }
                        }
                    });


                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getContext(), "Erreur lors de l'inscription :" + message, Toast.LENGTH_SHORT);
                    progressDialogloading.dismiss();

                }

            }
        });


    }




    // Intent pour retourner à la page MainActivity
    private void SendCurrentUserToEspacePersonnel() {

     Intent pagemainIntent = new Intent(getContext(),EspacePersonnel.class);
        pagemainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pagemainIntent);
        Fragment_Register.this.dismiss();

    }

    // Pour afficher le nom et prénom de chaque médecin

    public void fetchdata()
    {
        listener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren()) {

                    //theKey = mydata.getKey().toString();

                    list.add(mydata.child("prenom").getValue().toString()+ " " + mydata.child("nom").getValue().toString());
                    listkey.add(mydata.getKey().toString());


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
