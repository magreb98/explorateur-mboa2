package com.explorateurmboa.article_management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.explorateurmboa.article_management.models.Image;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
    Optional<Image> findByFileName(String fileName);
}