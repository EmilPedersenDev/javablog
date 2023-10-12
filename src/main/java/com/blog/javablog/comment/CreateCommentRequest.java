package com.blog.javablog.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CreateCommentRequest implements Serializable {
  @JsonProperty
  private long blogUserId;
  @JsonProperty
  private long articleId;
  @JsonProperty
  private String text;

  public CreateCommentRequest() {
  }

  public CreateCommentRequest(long blogUserId, long articleId, String text) {
    this.blogUserId = blogUserId;
    this.articleId = articleId;
    this.text = text;
  }

  public long getBlogUserId() {
    return blogUserId;
  }

  public void setBlogUserId(long blogUserId) {
    this.blogUserId = blogUserId;
  }

  public long getArticleId() {
    return articleId;
  }

  public void setArticleId(long articleId) {
    this.articleId = articleId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
