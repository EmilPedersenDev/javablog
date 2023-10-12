package com.blog.javablog.comment;

import com.blog.javablog.article.Article;
import com.blog.javablog.user.BlogUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
  @Id
  @GeneratedValue
  private long id;
  private String text;

  // Relationships
  @ManyToOne
//  @JsonBackReference(value = "articleComment")
  @JoinColumn(name = "article_id", nullable = false)
  @JsonIgnoreProperties(value = {"comments", "blogUser"})
  Article article;

  @ManyToOne
//  @JsonBackReference(value = "blogUserComment")
  @JsonIgnoreProperties(value = {"articles", "comments"})
  @JoinColumn(name = "blogUser_id", nullable = false)
  BlogUser blogUser;

  public Comment() {
  }

  public Comment(String text) {
    this.text = text;
  }

  public Comment(long id, String text, Article article, BlogUser blogUser) {
    this.id = id;
    this.text = text;
    this.article = article;
    this.blogUser = blogUser;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public BlogUser getBlogUser() {
    return blogUser;
  }

  public void setBlogUser(BlogUser blogUser) {
    this.blogUser = blogUser;
  }
}
