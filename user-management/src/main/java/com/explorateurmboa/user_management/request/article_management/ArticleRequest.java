package com.explorateurmboa.user_management.request.article_management;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ArticleRequest {

    private String titre;
    private String contenu;
    private String auteur;
    private boolean published;
    private String dateCreation;
    private String datePublication;
    private MultipartFile[] images; 

}
