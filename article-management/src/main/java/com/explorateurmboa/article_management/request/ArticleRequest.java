package com.explorateurmboa.article_management.request;

import org.springframework.web.multipart.MultipartFile;

public class ArticleRequest {

    private String titre;
    private String contenu;
    private String auteur;
    private boolean published;
    private String dateCreation;
    private String datePublication;
    private MultipartFile[] images; 

    // Getters et Setters
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public String getDatePublication() { return datePublication; }
    public void setDatePublication(String datePublication) { this.datePublication = datePublication; }

    public MultipartFile[] getImages() { return images; }
    public void setImages(MultipartFile[] images) { this.images = images; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }  
}
