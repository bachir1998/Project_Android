package com.example.project_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Fragment_seance extends DialogFragment implements AdapterView.OnItemSelectedListener
{
    private TextView textdate, textheuredeb, textheurefin;
    private Spinner spinner_activity, spinner_structure;
    DatePickerDialog.OnDateSetListener setListener;
    int heure1,minute1;
    int heure2,minute2;
    public Fragment_seance() {
        // le fragment est créé par la méthode newInstance
    }


    public static Fragment_seance newInstance(String title) {

        Fragment_seance frag = new Fragment_seance();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_seance, container);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_carte_bancaire, container, false);
        super.onViewCreated(view, savedInstanceState);



        textdate = view.findViewById(R.id.newdate);
        textheuredeb = view.findViewById(R.id.heuredeb);
        textheurefin = view.findViewById(R.id.heurefin);
        spinner_activity = view.findViewById(R.id.choisir_activity);
        spinner_structure = view.findViewById(R.id.choisir_strucure);
        spinner_activity.setOnItemSelectedListener(this);
        spinner_structure.setOnItemSelectedListener(this);




        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int  month  = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        textdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String Date = day+"/"+month+"/"+year;
                        textdate.setText(Date);

                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });


        textheuredeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initialiser Time Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override

                    public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                        //initialiser heure et minute
                        heure1 = hourofday;
                        minute1 = minute;
                        //initialise Calendar
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,heure1,minute1);
                        textheuredeb.setText(DateFormat.format("HH:mm aa",calendar));

                    }
                },24,0,true
                );

                timePickerDialog.updateTime(heure1,minute1);
                timePickerDialog.show();

            }


        });

        textheurefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initialiser Time Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override

                    public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                        //initialiser heure et minute
                        heure2 = hourofday;
                        minute2 = minute;
                        //initialise Calendar
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,heure2,minute2);
                        textheurefin.setText(DateFormat.format("HH:mm aa",calendar));

                    }
                },24,0,true
                );

                timePickerDialog.updateTime(heure2,minute2);
                timePickerDialog.show();

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
