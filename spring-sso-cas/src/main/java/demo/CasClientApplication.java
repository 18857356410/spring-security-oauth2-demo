package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b><code>CasClientApplication</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/5/31 15:37.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@SpringBootApplication
public class CasClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(CasClientApplication.class, args);
    System.out.println("http://localhost:9098/hello");
    System.out.println("http://localhost:9098/login");
    System.out.println("http://localhost:9098/logout");
  }

}
