package com.blog.javablog.comment;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface CommentService {
  List<Comment> getAllComments();

  Comment getComment(long id) throws IOException;

  void createComment(Comment comment, long articleId, long blogUserId) throws IOException, NoSuchElementException;

  void updateComment(long id, Comment comment) throws NoSuchElementException, IOException;

  void deleteComment(long id) throws NoSuchElementException;
}
