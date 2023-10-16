package com.blog.javablog.image;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@Service
@EnableCaching
public class ImageServiceImpl implements ImageService {

  private final ImageRepository repository;

  @Autowired
  public ImageServiceImpl(ImageRepository repository) {
    this.repository = repository;
  }

  @Override
  public String uploadImage(MultipartFile file) throws IOException {
    Image image = new Image();
    image.setName(file.getOriginalFilename());
    image.setType(file.getContentType());
    image.setData(ImageUtil.compressImage(file.getBytes()));
    repository.save(image);
    return file.getOriginalFilename();
  }

  @Override
  @Transactional
  public Map<String, ?> getImage(String name) throws IOException, DataFormatException {
    Image image = repository.findByName(name).orElseThrow(() -> new IOException("No image was found"));
    byte[] imageData = ImageUtil.decompressImage(image.getData());
    Map<String, ?> imageResponse = new HashMap<>() {{
      put("type", image.getType());
      put("imageData", imageData);
    }};
    return imageResponse;
  }
}
