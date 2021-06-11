package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangzongyi
 */
@SpringBootApplication
@Slf4j
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
    log.info("启动成功,默认账号：admin,默认密码：admin");
    log.info("官方提供的数据库脚本：https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql");
    log.info("启动成功,获取授权码：http://localhost:8080/oauth/authorize?client_id=client&response_type=code");
  }
}
