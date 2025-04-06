package com.explorateurmboa.user_management.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.explorateurmboa.user_management.request.article_management.ArticleRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/article-management")
public class ArticleController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String ARTICLE_SERVICE_BASE_URL = "http://localhost:8085/api/articles";
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('JOURNALIST')")
    public ResponseEntity<String> getArticle(){
        String endpoint = ARTICLE_SERVICE_BASE_URL;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des articles."+ e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JOURNALIST')")
    public ResponseEntity<String> getOne(@PathVariable Long id){
        String endpoint = ARTICLE_SERVICE_BASE_URL +"/" + id;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);
            return response;
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Article Inexistant");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('JOURNALIST')")
    public ResponseEntity<String> CreateArticle(@ModelAttribute ArticleRequest articleRequest, @PathVariable Long id){
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    ARTICLE_SERVICE_BASE_URL,
                HttpMethod.POST, 
                new HttpEntity<>(articleRequest), 
                String.class
                );

            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'article.");

        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JOURNALIST')")
    public ResponseEntity<String> updateArticle(@ModelAttribute ArticleRequest articleRequest, @PathVariable Long id){
        String endpoint = ARTICLE_SERVICE_BASE_URL + "/" + id;
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                endpoint, 
                HttpMethod.PUT, 
                new HttpEntity<>(articleRequest), 
                String.class
                );

            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'article.");

        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JOURNALIST')")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id){
        String endpoint = ARTICLE_SERVICE_BASE_URL + "/" + id;

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                endpoint, 
                HttpMethod.DELETE, 
                null, 
                String.class
                );

                if(response.getStatusCode() == HttpStatus.OK){
                    return ResponseEntity.ok("Article supprimé avec succès.");
                }else{
                    return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
                }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de l'article.");

        }
    }

}
