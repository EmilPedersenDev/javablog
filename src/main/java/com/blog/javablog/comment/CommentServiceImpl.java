package com.blog.javablog.comment;

import com.blog.javablog.article.Article;
import com.blog.javablog.article.ArticleRepository;
import com.blog.javablog.user.BlogUser;
import com.blog.javablog.user.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@EnableCaching
public class CommentServiceImpl implements CommentService {

  CommentRepository repository;
  ArticleRepository articleRepository;
  BlogUserRepository blogUserRepository;

  @Autowired
  public CommentServiceImpl(CommentRepository repository, ArticleRepository articleRepository, BlogUserRepository blogUserRepository) {
    this.repository = repository;
    this.articleRepository = articleRepository;
    this.blogUserRepository = blogUserRepository;
  }

  @Override
  @Cacheable(value = "comments")
  public List<Comment> getAllComments() {
    return repository.findAll();
  }

  @Override
  public Comment getComment(long id) throws IOException {
    return repository.findById(id).orElseThrow(() -> new IOException("Comment was not found in db"));
  }

  @Override
  @CacheEvict(value = "comments", allEntries = true)
  public void createComment(Comment comment, long articleId, long blogUserId) throws IOException, NoSuchElementException {
    if (articleId < 1 || blogUserId < 1) {
      throw new NoSuchElementException("No article id or user id was provided");
    }

    Article article = articleRepository.findById(articleId)
      .orElseThrow(() -> new IOException("No article was found in the db"));
    BlogUser blogUser = blogUserRepository.findById(blogUserId)
      .orElseThrow(() -> new IOException("No user was found in the db"));

    comment.setArticle(article);
    comment.setBlogUser(blogUser);

    repository.save(comment);
  }

  @Override
  @CacheEvict(value = "comments", allEntries = true)
  public void updateComment(long id, Comment comment) throws NoSuchElementException, IOException {
    if (id < 1) {
      throw new NoSuchElementException("No id was provided");
    }
    Comment savedComment = repository.findById(id).orElseThrow(() -> new IOException("No comment was found in the db"));
    savedComment.setText(comment.getText());
    repository.save(savedComment);
  }

  @Override
  public void deleteComment(long id) throws NoSuchElementException {
    if (id < 1) {
      throw new NoSuchElementException("No comment id was provided");
    }
    repository.deleteById(id);
  }
}
