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
    log.info("第一步 > 登录 > http://localhost:9004/oauth2/authorization/client3");
    log.info("第二步 > 获取Token >  http://localhost:9004/token");
    log.info("第三步 > 访问 需要system:list权限的接口  http://localhost:9004/zhangsan");
    log.info("第四步 > : http://localhost:9004/wangwu");

  }

}
