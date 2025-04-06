package com.explorateurmboa.article_management.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private String fileType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    // Constructeurs
    public Image() {}

    public Image(String fileName, String filePath, String fileType, Article article) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.article = article;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
}
