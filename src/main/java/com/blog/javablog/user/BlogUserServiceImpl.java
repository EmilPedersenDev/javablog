package com.blog.javablog.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BlogUserServiceImpl implements BlogUserService {
  BlogUserRepository repository;

  @Autowired
  public BlogUserServiceImpl(BlogUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public BlogUser getBlogUser(long id) {
    Optional<BlogUser> blogUser = repository.findById(id);
    return blogUser.orElse(null);
  }

  @Override
  public void createBlogUser(BlogUser blogUser) throws NoSuchElementException {
    if (blogUser == null || blogUser.getId() < 1) {
      throw new NoSuchElementException("No user was provided");
    }
    repository.save(blogUser);
  }

  @Override
  public void updateBlogUser(BlogUser blogUser) throws NoSuchElementException, IOException {
    if (blogUser == null) {
      throw new NoSuchElementException("No user was found");
    }
    BlogUser savedBlogUser = repository.findById(blogUser.getId())
      .orElseThrow(() -> new IOException("No user was found in the db"));

    savedBlogUser.setFirstname(blogUser.getFirstname());
    savedBlogUser.setLastname(blogUser.getLastname());

    repository.save(savedBlogUser);
  }

  @Override
  public void deleteBlogUser(long id) throws NoSuchElementException {
    if (id < 1) {
      throw new NoSuchElementException("Not a valid id");
    }
    repository.deleteById(id);
  }
}
