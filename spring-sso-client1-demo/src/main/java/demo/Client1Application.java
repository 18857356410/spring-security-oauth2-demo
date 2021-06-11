package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b><code>Client1Application</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/29 19:34.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@SpringBootApplication
@Slf4j
public class Client1Application {

    public static void main(String[] args) {
        SpringApplication.run(Client1Application.class, args);
        log.info("启动成功，http://localhost:9001/");
        log.info("启动成功，http://localhost:9001/user/info");
    }

}
