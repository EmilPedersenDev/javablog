package com.blog.javablog.user;

import java.io.IOException;
import java.util.NoSuchElementException;

public interface BlogUserService {
  BlogUser getBlogUser(long id);

  void createBlogUser(BlogUser blogUser) throws NoSuchElementException;

  void updateBlogUser(BlogUser blogUser) throws NoSuchElementException, IOException;

  void deleteBlogUser(long id) throws NoSuchElementException;
}
