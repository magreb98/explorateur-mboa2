package com.explorateurmboa.article_management.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.explorateurmboa.article_management.models.Article;
import java.util.List;
import java.util.Date;



//Remove @RepositoryRestResource below to disable auto REST api:
//@RepositoryRestResource
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>{

    Optional<Article> findByTitre(String titre);
    List<Article> findByDatePublication(Date date_publication);
}