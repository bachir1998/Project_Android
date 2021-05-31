package com.example.project_android;

public class Personne
{
    public String nom;
    public String prenom;
    public String password;
    public String email;
    public String specialite;
    public String UID;


    public Personne( String email, String password,String prenom, String nom, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.specialite = specialite;
    }

    public Personne()
    {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
