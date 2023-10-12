package com.blog.javablog.article;

import com.blog.javablog.comment.Comment;
import com.blog.javablog.user.BlogUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Article {
  @Id
  @GeneratedValue()
  private long id;
  private String text;

  // Relationships
  @ManyToOne
  @JoinColumn(name = "blogUser_id", nullable = false)
//  @JsonBackReference(value = "blogUserArticle")
  @JsonIgnoreProperties(value = {"articles", "comments"})
  BlogUser blogUser;

  @OneToMany(mappedBy = "article")
//  @JsonManagedReference(value = "articleComment")
  @JsonIgnoreProperties(value = {"article", "blogUser"})
  @JsonIgnore
  List<Comment> comments;

  public Article() {
  }

  public Article(long id, String author, String text, BlogUser blogUser, List<Comment> comments) {
    this.id = id;
    this.text = text;
    this.blogUser = blogUser;
    this.comments = comments;
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

  public BlogUser getBlogUser() {
    return blogUser;
  }

  public void setBlogUser(BlogUser blogUser) {
    this.blogUser = blogUser;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }
}
