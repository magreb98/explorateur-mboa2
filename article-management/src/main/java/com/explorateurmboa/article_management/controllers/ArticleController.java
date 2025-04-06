package com.explorateurmboa.article_management.controllers;

import com.explorateurmboa.article_management.models.Article;
import com.explorateurmboa.article_management.models.Image;
import com.explorateurmboa.article_management.request.ArticleRequest;
import com.explorateurmboa.article_management.services.ArticleService;
import com.explorateurmboa.article_management.services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    private final ArticleService articleService;
    private final ImageService imageService;

    public ArticleController(ArticleService articleService, ImageService imageService) {
        this.articleService = articleService;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@ModelAttribute ArticleRequest request) {
        Article article = articleService.createArticle(
                request.getTitre(),
                request.getContenu(),
                request.getAuteur(),
                request.isPublished(),
                request.getDateCreation(),
                request.getDatePublication(),
                request.getImages()
        );

        return ResponseEntity.ok(article);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(article);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @ModelAttribute ArticleRequest request) {
        
        Article updatedArticle = articleService.updateArticle(
                id,
                request.getTitre(),
                request.getContenu(),
                request.isPublished(),
                request.getDateCreation(),
                request.getDatePublication(),
                request.getImages()
        );

        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("image/{filepath}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filepath) {
        return  imageService.getImage(filepath);
    }
}
