package com.blog.javablog.image;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/images")
public class ImageController {

  ImageRepository repository;

  ImageService imageService;

  @Autowired
  public ImageController(ImageRepository repository, ImageService imageService) {
    this.repository = repository;
    this.imageService = imageService;
  }

  @GetMapping("/{name}")
  public ResponseEntity<?> getImage(@Valid @PathVariable String name) {
    try {
      Map<String, ?> imageResponse = imageService.getImage(name);
      String imageType = (String) imageResponse.get("type");
      byte[] imageData = (byte[]) imageResponse.get("imageData");

      if (imageType == null || imageData == null) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get the image data");
      }

      return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.valueOf(imageType))
        .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS).cachePublic())
        .body(imageData);
    } catch (IOException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
    try {
      String imageUploadResponse = imageService.uploadImage(file);
      return ResponseEntity.ok(imageUploadResponse);
    } catch (IOException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}
