package com.blog.javablog.comment;

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

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/comments")
public class CommentController {
  CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public ResponseEntity<List<Comment>> getAllComments() {
    try {
      return ResponseEntity.ok(commentService.getAllComments());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Comment> getComment(@PathVariable long id) {
    try {
      return ResponseEntity.ok(commentService.getComment(id));
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
    try {
      commentService.createComment(
        new Comment(createCommentRequest.getText()),
        createCommentRequest.getArticleId(),
        createCommentRequest.getBlogUserId()
      );
      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateComment(@RequestBody Comment comment, @PathVariable long id) {
    try {
      commentService.updateComment(id, comment);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteComment(@PathVariable long id) {
    try {
      commentService.deleteComment(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
