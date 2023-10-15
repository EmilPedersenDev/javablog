package com.blog.javablog.article;

import com.blog.javablog.image.ImageService;
import com.blog.javablog.util.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/articles")
public class ArticleController extends AbstractController {
  ArticleRepository repository;
  ArticleService articleService;
  ImageService imageService;

  @Autowired
  public ArticleController(ArticleRepository repository, ArticleService articleService, ImageService imageService) {
    this.repository = repository;
    this.articleService = articleService;
    this.imageService = imageService;
  }

  @GetMapping
  public ResponseEntity<List<Article>> getAllArticles(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
    try {
      List<Article> articles = articleService.getAllArticles(offset, limit);
      return ResponseEntity.ok(articles);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @GetMapping("/total-amount")
  public ResponseEntity<Integer> getArticlesTotalAmount() {
    try {
      int articles = articleService.getArticlesTotalAmount();
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

  @GetMapping("/image/{name}")
  public ResponseEntity<?> getArticleImage(@PathVariable String name) {
    try {
      if (name == null) {
        throw new NoSuchElementException("No image name provided");
      }
      Map<String, ?> image = imageService.getImage(name);

      if (image.get("imageData") != null && image.get("type") != null) {
        byte[] imageFile = (byte[]) image.get("imageData");
        String imageType = (String) image.get("type");

        return ResponseEntity.status(HttpStatus.OK)
          .contentType(MediaType.valueOf(imageType))
          .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS).cachePublic())
          .body(imageFile);
      } else {
        throw new NoSuchElementException("No image was found");
      }
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity createArticle(@Valid @RequestBody Article article, @RequestParam("blogUserId") long blogUserId, HttpServletRequest request) {
    try {
      articleService.createArticle(article, blogUserId, getBaseUrl(request));
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
