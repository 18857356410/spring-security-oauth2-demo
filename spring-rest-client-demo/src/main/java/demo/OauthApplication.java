package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b><code>OAuthApplication</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/24 15:10.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@SpringBootApplication
@Slf4j
public class OauthApplication {

  public static void main(String[] args) {
    SpringApplication.run(OauthApplication.class, args);
    log.info("http://localhost:9003/oauth/login/github");
    log.info("http://localhost:9003/oauth/login/ruoyi");
  }

}
