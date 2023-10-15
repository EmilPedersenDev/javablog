package com.blog.javablog.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.zip.DataFormatException;

public interface ImageService {
  String uploadImage(MultipartFile file) throws IOException;

  Map<String, ?> getImage(String name) throws IOException, DataFormatException;
}
