package com.blog.javablog.article;

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
import java.util.Optional;

@Service
@EnableCaching
public class ArticleServiceImpl implements ArticleService {
  ArticleRepository repository;

  BlogUserRepository blogUserRepository;

  @Autowired
  public ArticleServiceImpl(ArticleRepository repository, BlogUserRepository blogUserRepository) {
    this.repository = repository;
    this.blogUserRepository = blogUserRepository;
  }


  @Override
  @Cacheable(value = "articles")
  public List<Article> getAllArticles() {
    return repository.findAll();
  }

  @Override
  public Article getArticle(long id) {
    Optional<Article> article = repository.findById(id);

    if (article.isPresent()) {
      BlogUser user = article.get().getBlogUser();
      System.out.println(user);
    }
    return article.orElse(null);
  }

  @Override
  @CacheEvict(value = "articles", allEntries = true)
  public void createArticle(Article article, long blogUserId) throws NoSuchElementException, IOException {
    if (article == null || article.getId() < 1) {
      throw new NoSuchElementException("No article was provided");
    }

    if (blogUserId < 1) {
      throw new NoSuchElementException("No user id was provided");
    }

    Optional<BlogUser> blogUser = blogUserRepository.findById(blogUserId);

    if (blogUser.isEmpty()) {
      throw new IOException("No user was found in the db");
    }

    article.setBlogUser(blogUser.get());
    repository.save(article);
  }

  @Override
  @CacheEvict(value = "articles", allEntries = true)
  public void updateArticle(long id, Article article) throws NoSuchElementException, IOException {
    if (id < 1) {
      throw new NoSuchElementException("No article id was found");
    }
    Article savedArticle = repository.findById(id)
      .orElseThrow(() -> new IOException("No article was found in the db"));
    savedArticle.setText(article.getText());
    repository.save(savedArticle);
  }

  @Override
  public void deleteArticle(long id) throws NoSuchElementException {
    if (id < 1) {
      throw new NoSuchElementException("Not a valid id");
    }
    repository.deleteById(id);
  }
}
