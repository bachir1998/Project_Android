package com.example.project_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Fragment_activity extends DialogFragment
{
    private EditText titre, description, duree;
    private Button creer;
    public Fragment_activity() {
        // le fragment est créé par la méthode newInstance
    }


    public static Fragment_activity newInstance(String title) {

        Fragment_activity frag = new Fragment_activity();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_activity, container);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_carte_bancaire, container, false);
        super.onViewCreated(view, savedInstanceState);


        titre = view.findViewById(R.id.titre_activity);
        description = view.findViewById(R.id.description_activity);
        duree = view.findViewById(R.id.duree_activity);
        creer = view.findViewById(R.id.btn_creer_activity);




    }


}
