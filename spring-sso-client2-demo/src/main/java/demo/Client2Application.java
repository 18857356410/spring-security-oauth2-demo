package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangzongyi
 */
@SpringBootApplication
@Slf4j
public class Client2Application {

    public static void main(String[] args) {
        SpringApplication.run(Client2Application.class, args);
        log.info("Client1 启动成功！http://localhost:9002/");
        log.info("启动成功，http://localhost:9001/user/info");

    }

}
