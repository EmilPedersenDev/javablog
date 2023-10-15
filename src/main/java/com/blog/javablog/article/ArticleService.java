package com.blog.javablog.article;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface ArticleService {
  List<Article> getAllArticles(int offset, int limit);

  int getArticlesTotalAmount();

  Article getArticle(long id);

  void createArticle(Article article, long blogUserId, String baseUrl) throws NoSuchElementException, IOException;

  void updateArticle(long id, Article article) throws NoSuchElementException, IOException;

  void deleteArticle(long id) throws NoSuchElementException;
}
