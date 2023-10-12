package com.blog.javablog.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/articles")
public class ArticleController {

  ArticleRepository repository;

  ArticleService articleService;

  @Autowired
  public ArticleController(ArticleRepository repository, ArticleService articleService) {
    this.repository = repository;
    this.articleService = articleService;
  }

  @GetMapping
  public ResponseEntity<List<Article>> getAllArticles() {
    try {
      List<Article> articles = articleService.getAllArticles();
      return ResponseEntity.ok(articles);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Article> getArticle(@PathVariable long id) {
    try {
      Article article = articleService.getArticle(id);

      if (article == null) {
        throw new IOException("No article was found");
      } else {
        return ResponseEntity.ok(article);
      }
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity createArticle(@RequestBody Article article, @RequestParam("blogUserId") long blogUserId) {
    try {
      articleService.createArticle(article, blogUserId);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateArticle(@RequestBody Article article, @PathVariable long id) {
    try {
      articleService.updateArticle(id, article);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteArticle(@PathVariable long id) {
    try {
      articleService.deleteArticle(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
