package com.blog.javablog.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

  ArticleRepository repository;

  @Autowired
  public ArticleController(ArticleRepository repository) {
    this.repository = repository;
  }


  @GetMapping
  public ResponseEntity<List<Article>> getAllArticles() {
    try {
      List<Article> articles = repository.findAll();
      return ResponseEntity.ok(articles);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
