package com.blog.javablog.article;

import com.blog.javablog.comment.Comment;
import com.blog.javablog.user.BlogUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
public class Article {
  @Id
  @GeneratedValue()
  private long id;

  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "The text attribute is required.")
  @Column(columnDefinition = "TEXT")
  private String text;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated;

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

  public Article(long id, String title, String text, Date created, Date updated, BlogUser blogUser, List<Comment> comments) {
    this.id = id;
    this.title = title;
    this.text = text;
    this.blogUser = blogUser;
    this.comments = comments;
    this.created = created;
    this.updated = updated;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }
}
