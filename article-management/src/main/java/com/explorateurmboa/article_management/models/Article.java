package com.explorateurmboa.article_management.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "articles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titre;

    @NotBlank
    @Column(name = "contenu", columnDefinition = "TEXT")
    private String contenu;

    @NotBlank
    private String auteur;

    @NotBlank
    private boolean published;

    @NotBlank
    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @NotBlank
    @Column(name = "date_publication")
    private LocalDate datePublication;

    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    
    public Article() {}

    public Article(String titre, String contenu, String auteur, boolean published, LocalDate dateCreation, LocalDate datePublication, List<Image> images) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.published = published;
        this.dateCreation = dateCreation;
        this.datePublication = datePublication;
        this.images = images;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public boolean isPublished() { return published; }
    public void setPublished(boolean published) { this.published = published; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public LocalDate getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDate datePublication) { this.datePublication = datePublication; }

    public List<Image> getImages() { return images; }
    public void setImages(List<Image> images) { this.images = images; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }


    
}
