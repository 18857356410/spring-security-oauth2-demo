package demo.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

/**
 * <b><code>TokenConfig</code></b>
 * <p/>
 * 资源类 使用JWT 对称加密
 * <p/>
 * <b>Creation Time:</b> 2021/3/22 16:36.
 *
 * @author FS_425 WangZongYi
 * @since mengxuegu-cloud-oauth2-parent 1.0
 */
@Configuration
@Slf4j
public class TokenConfig {


  @Bean(value = "resourceTokenConverter")
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();

    // 非对称加密：
    String publicKey = null;

    try {
      ClassPathResource resource = new ClassPathResource("/public.txt");
      publicKey = IOUtils.toString(resource.getInputStream(), "UTF-8");
      log.info("公钥Public_Key:{}", publicKey);
    } catch (IOException e) {
      log.error("获取公钥出现异常:", e);
    }
    tokenConverter.setVerifierKey(publicKey);

    return tokenConverter;
  }


  @Bean
  public TokenStore tokenStore() {
    //  返回jwt
    return new JwtTokenStore(jwtAccessTokenConverter());
  }


}
