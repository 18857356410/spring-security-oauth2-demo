package demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangzongyi
 */
@SpringBootApplication
@Slf4j
public class OAuth2ClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(OAuth2ClientApplication.class, args);
    log.info("使用a标签登录:http://localhost:9004/oauth2/authorization/client3");
    log.info("无需登录获取wangwu的信息: http://localhost:9004/wangwu");
    log.info("无需登录获取wangwu的信息: http://localhost:9004/user");
    log.info("需要system:list权限获取zhangsan信息: http://localhost:9004/zhangsan");
  }

}
