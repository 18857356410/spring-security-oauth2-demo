package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b><code>ResourceClientApplication</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/29 19:55.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@SpringBootApplication
@Slf4j
public class ResourceClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(ResourceClientApplication.class, args);
    log.info("启动成功，http://localhost:9003/product/list");
    log.info("启动测试，请使用PostMan 测试，因为请求头需要携带Token");
  }
}
