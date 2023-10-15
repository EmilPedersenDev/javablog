package com.blog.javablog.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Image {
  @Id
  @GeneratedValue
  private long id;
  @NotBlank(message = "The name attribute is required.")
  private String name;
  private String type;
  @Lob
  @Column(name = "data")
  private byte[] data;

  public Image() {
  }

  public Image(long id, String name, String type, byte[] data) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.data = data;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
