package com.explorateurmboa.article_management.services;

import com.explorateurmboa.article_management.models.Article;
import com.explorateurmboa.article_management.models.Image;
import com.explorateurmboa.article_management.repositories.ArticleRepository;
import com.explorateurmboa.article_management.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public ArticleService(ArticleRepository articleRepository, ImageRepository imageRepository, ImageService imageService) {
        this.articleRepository = articleRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    
    public Article createArticle(String titre, String contenu, String auteur, boolean published, String dateCreationStr,
                                 String datePublicationStr, MultipartFile[] images) {

        LocalDate dateCreation = LocalDate.parse(dateCreationStr);
        LocalDate datePublication = LocalDate.parse(datePublicationStr);

        Article article = new Article(titre, contenu, auteur, published, dateCreation, datePublication, new ArrayList<>());
        article = articleRepository.save(article);

        if (images != null) {
            saveImages(images, article);
        }

        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return (List<Article>) articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    
    public Article updateArticle(Long id, String titre, String contenu, boolean published,
                                 String dateCreationStr, String datePublicationStr, MultipartFile[] images) {

        Article article = getArticleById(id);
        article.setTitre(titre);
        article.setContenu(contenu);
        article.setPublished(published);
        article.setDateCreation(LocalDate.parse(dateCreationStr));
        article.setDatePublication(LocalDate.parse(datePublicationStr));

        // Supprimer les anciennes images et sauvegarder les nouvelles
        if (images != null && images.length > 0) {
            deleteImages(article);
            saveImages(images, article);
        }

        return articleRepository.save(article);
    }

    /**
     * 📌 Supprimer un article et ses images
     */
    public void deleteArticle(Long id) {
        Article article = getArticleById(id);
        deleteImages(article);
        articleRepository.delete(article);
    }

    /**
     * 📌 Sauvegarder les images pour un article
     */
    private void saveImages(MultipartFile[] images, Article article) {
        for (MultipartFile file : images) {
            String filePath = imageService.saveImage(file);
            Image image = new Image(file.getOriginalFilename(), filePath, file.getContentType(), article);
            imageRepository.save(image);
            article.getImages().add(image);
        }
    }

    /**
     * 📌 Supprimer les images d'un article
     */
    private void deleteImages(Article article) {
        List<Image> images = article.getImages();
        for (Image image : images) {
            imageService.deleteImage(image.getFilePath());
            imageRepository.delete(image);
        }
        images.clear();
    }
}
