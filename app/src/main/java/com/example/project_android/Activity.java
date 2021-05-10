package com.example.project_android;

public class Activity
{
    public String titre;
    public String description;
    public String duree;
    public int image;

    public Activity(String titre, String description, String duree, int image) {
        this.titre = titre;
        this.description = description;
        this.duree = duree;
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

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }
}
