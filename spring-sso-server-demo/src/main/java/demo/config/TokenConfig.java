package demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author wangzongyi
 */
@Configuration
public class TokenConfig {

  /**
   * 对称加密自定义的秘钥
   */
  private static final String SIGN_KEY = "DEMO";


  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    // 设置JWT 对称加密方式
    // jwtAccessTokenConverter.setSigningKey(SIGN_KEY);

    ClassPathResource classPathResource = new ClassPathResource("/oauth2.jks");
    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "oauth2".toCharArray());
    jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2"));

    return jwtAccessTokenConverter;
  }
}
