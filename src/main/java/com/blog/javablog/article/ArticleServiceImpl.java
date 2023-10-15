package com.blog.javablog.article;

import com.blog.javablog.AppConfig;
import com.blog.javablog.image.Image;
import com.blog.javablog.image.ImageRepository;
import com.blog.javablog.image.ImageUtil;
import com.blog.javablog.user.BlogUser;
import com.blog.javablog.user.BlogUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@EnableCaching
public class ArticleServiceImpl implements ArticleService {
  ArticleRepository repository;
  BlogUserRepository blogUserRepository;
  ImageRepository imageRepository;

  AppConfig appConfig;

  @Autowired
  public ArticleServiceImpl(ArticleRepository repository, BlogUserRepository blogUserRepository, AppConfig appConfig, ImageRepository imageRepository) {
    this.repository = repository;
    this.blogUserRepository = blogUserRepository;
    this.appConfig = appConfig;
    this.imageRepository = imageRepository;
  }

  @Override
  @Transactional
  @Cacheable(value = "articles")
  public List<Article> getAllArticles(int offset, int limit) {
    return repository.getArticlesByOffsetAndLimit(offset, limit);
  }

  @Override
  @Cacheable(value = "articlesTotalAmount")
  public int getArticlesTotalAmount() {
    return repository.getArticlesTotalAmount();
  }

  @Override
  @Transactional
  public Article getArticle(long id) {
    Optional<Article> article = repository.findById(id);
    return article.orElse(null);
  }

  @Override
  @CacheEvict(value = "articles", allEntries = true)
  public void createArticle(Article article, long blogUserId, String baseUrl) throws NoSuchElementException, IOException {
    if (blogUserId < 1 || article == null || baseUrl == null) {
      throw new NoSuchElementException("Invalid parameters in method");
    }

    saveArticleImages(article, baseUrl);

    //    Todo: Fix html sanitation
    //    String escapedArticleText = appConfig.xssHtmlSanitizer().sanitize(article.getText());
    //    article.setText(escapedArticleText);

    Optional<BlogUser> blogUser = blogUserRepository.findById(blogUserId);

    if (blogUser.isEmpty()) {
      throw new IOException("No user was found in the db");
    }

    article.setBlogUser(blogUser.get());
    repository.save(article);
  }

  @Override
  @CacheEvict(value = "articles", allEntries = true)
  public void updateArticle(long id, Article article) throws NoSuchElementException, IOException {
    if (id < 1) {
      throw new NoSuchElementException("No article id was found");
    }
    Article savedArticle = repository.findById(id)
      .orElseThrow(() -> new IOException("No article was found in the db"));
    savedArticle.setText(article.getText());
    repository.save(savedArticle);
  }

  @Override
  public void deleteArticle(long id) throws NoSuchElementException {
    if (id < 1) {
      throw new NoSuchElementException("Not a valid id");
    }
    repository.deleteById(id);
  }

  private void saveArticleImages(Article article, String baseUrl) throws NoSuchElementException, IOException {
    String formattedArticleText = article.getText();
    String regexPattern = "src\\s*=\\s*\"(.+?)\"";
    Pattern pattern = Pattern.compile(regexPattern);
    Matcher matcher = pattern.matcher(article.getText());

    while (matcher.find()) {
      String imageSource = matcher.group(1);
      String imageBase64Str = imageSource.split(",")[1];

      File newImageFile = ImageUtil.createImageFile(imageBase64Str);
      String uniqueName = "image_" + UUID.randomUUID().toString().replaceAll("-", "");

      formattedArticleText = formattedArticleText.replaceFirst(Pattern.quote(imageSource), baseUrl + "/articles/image/" + uniqueName);
      createArticleImage(newImageFile, uniqueName);
    }

    article.setText(formattedArticleText);
  }

  private void createArticleImage(File articleFile, String fileName) throws NoSuchElementException, IOException {
    if (articleFile == null || fileName == null) {
      throw new NoSuchElementException("No file or filename was provided");
    }

    Image newArticleImage = new Image();
    newArticleImage.setName(fileName);
    newArticleImage.setType("image/jpeg");
    newArticleImage.setData(ImageUtil.compressImage(Files.readAllBytes(articleFile.toPath())));
    imageRepository.save(newArticleImage);
  }
}
