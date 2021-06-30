package demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangzongyi
 */
@SpringBootApplication
@Slf4j
public class OtherOAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(OtherOAuthApplication.class, args);
    log.info("http://localhost:9004/");
    log.info("http://localhost:9004/user");

    log.info("前端点击自动访问: http://localhost:9004/oauth2/authorization/client3");
    log.info("http://192.168.108.24:32038/oauth/authorize?client_id=client3&response_type=code");



  }

}
