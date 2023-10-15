package com.blog.javablog.article;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>, PagingAndSortingRepository<Article, Long> {
  @Transactional
  @Query(value = "select * from article a order by id desc offset :offset limit :limit", nativeQuery = true)
  List<Article> getArticlesByOffsetAndLimit(int offset, int limit);

  @Transactional
  @Query(value = "select count(*) from article a", nativeQuery = true)
  int getArticlesTotalAmount();
}
