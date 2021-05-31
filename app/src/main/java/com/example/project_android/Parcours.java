package com.example.project_android;

public class Parcours
{
    public String titre;
    public String description;
    public String categorie;
    public int image;
    public String patient;
    public String intervenant;

    public Parcours( String categorie,String description,String intervenant,String patient,String titre) {
       this.intervenant = intervenant;
       this.patient = patient;
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(String intervenant) {
        this.intervenant = intervenant;
    }

   /* public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
*/
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
