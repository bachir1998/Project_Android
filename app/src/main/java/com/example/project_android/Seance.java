package com.example.project_android;

public class Seance
{
    public String date;
    public  String heure_debut;
    public String Heure_fin;
    public  int image;
    public  Structure structure;
    public Activity activity;


    public Seance(String date, String heure_debut, String heure_fin, int image,Activity activity,Structure structure) {
        this.activity = activity;
        this.structure = structure;
        this.date = date;
        this.heure_debut = heure_debut;
        Heure_fin = heure_fin;
        this.image = image;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return Heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        Heure_fin = heure_fin;
    }
}
