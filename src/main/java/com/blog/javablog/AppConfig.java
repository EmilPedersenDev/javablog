package com.blog.javablog;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
  @Bean
  @Scope("singleton")
  public PolicyFactory xssHtmlSanitizer() {
    return new HtmlPolicyBuilder()
      .allowElements("a", "p", "h1", "h2", "h3", "ol", "ul", "li", "strong", "em", "u", "span", "img")
      .allowUrlProtocols("https")
      .allowAttributes("href").onElements("a")
      .allowAttributes("src").onElements("img", "p")
      .requireRelNofollowOnLinks()
      .toFactory();
  }
}
