package com.blog.javablog.user;

import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class BlogUserController {
  BlogUserRepository repository;
  BlogUserService blogUserService;

  @Autowired
  public BlogUserController(BlogUserRepository repository, BlogUserService blogUserService) {
    this.repository = repository;
    this.blogUserService = blogUserService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<BlogUser> getBlogUser(@PathVariable long id) {
    try {
      BlogUser blogUser = blogUserService.getBlogUser(id);

      if (blogUser == null) {
        throw new IOException("No user was found");
      } else {
        return ResponseEntity.ok(blogUser);
      }
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<Object> createBlogUser(@Valid @RequestBody BlogUser blogUser) {
    try {
      blogUserService.createBlogUser(blogUser);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity<Object> updateArticle(@Valid @RequestBody BlogUser blogUser) {
    try {
      blogUserService.updateBlogUser(blogUser);
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
      blogUserService.deleteBlogUser(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
