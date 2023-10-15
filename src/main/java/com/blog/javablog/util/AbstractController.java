package com.blog.javablog.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class AbstractController {
  public AbstractController() {
  }

  public String getBaseUrl(HttpServletRequest request) throws MalformedURLException {
    String fullApiUrl = request.getRequestURL().toString();
    URL url = new URL(fullApiUrl);
    return url.getProtocol() + "://" + url.getAuthority();
  }
}
