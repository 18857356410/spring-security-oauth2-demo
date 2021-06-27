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
    log.info("http://localhost:9004/demo/string");
    log.info("http://auth-server:9095/oauth/authorize?client_id=client3&response_type=code");
  }

}
