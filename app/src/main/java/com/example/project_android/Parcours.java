package com.example.project_android;

public class Parcours
{
    public String titre;
    public String description;
    public String categorie;
    public int image;

    public Parcours(String titre, String description, String categorie, int image) {
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

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
