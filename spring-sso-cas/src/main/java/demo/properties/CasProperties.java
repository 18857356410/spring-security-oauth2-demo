package demo.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>CasProperties</code></b>
 * <p/>
 * CAS 服务端初始化属性配置
 * <p/>
 * <b>Creation Time:</b> 2021/5/31 17:55.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Configuration
@Data
public class CasProperties {


  @Value("${spring.cas.keySecurity}")
  private String keySecurity;


  /**
   * cas 服务端地址
   */
  @Value("${spring.cas.server.serverUrlPrefix}")
  private String serverUrlPrefix;


  /**
   * cas 服务端登录url
   */
  @Value("${spring.cas.server.serverLoginUrl}")
  private String serverLoginUrl;


  /**
   * CAS 服务端注销url
   */
  @Value("${spring.cas.server.serverLogoutUrl}")
  private String serverLogoutUrl;


  /**
   * 接入客户端的服务器地址
   */
  @Value("${spring.cas.service.hostUrl}")
  private String hostUrl;

  /**
   * 接入客户端登录url
   */
  @Value("${spring.cas.service.loginUrl}")
  private String loginUrl;


  /**
   * 接入客户端注销url
   */
  @Value("${spring.cas.service.logoutUrl}")
  private String logoutUrl;

}
