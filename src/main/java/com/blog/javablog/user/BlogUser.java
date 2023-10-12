package com.blog.javablog.user;

import com.blog.javablog.article.Article;
import com.blog.javablog.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class BlogUser {
  @Id
  @GeneratedValue
  private long id;
  @NotBlank(message = "The country is required.")
  private String firstname;
  @NotBlank(message = "The country is required.")
  private String lastname;

  // Relationships
  @OneToMany(mappedBy = "blogUser")
//  @JsonManagedReference(value = "blogUserArticle")
  @JsonIgnoreProperties(value = {"blogUser", "comments"})
  List<Article> articles;

  @OneToMany(mappedBy = "blogUser")
//  @JsonManagedReference(value = "blogUserComment")
  @JsonIgnoreProperties(value = {"blogUser", "articles"})
  List<Comment> comments;

  public BlogUser() {
  }

  public BlogUser(long id, String firstname, String lastname, List<Article> articles, List<Comment> comments) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.articles = articles;
    this.comments = comments;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }


  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public List<Article> getArticles() {
    return articles;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
