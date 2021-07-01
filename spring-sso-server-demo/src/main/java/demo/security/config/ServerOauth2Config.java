package demo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @author wangzongyi
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class ServerOauth2Config extends AuthorizationServerConfigurerAdapter {


  /**
   * 数据库方式获取UserDetail
   */
  @Autowired
  private UserDetailsService customUserDetailsService;


  /**
   * 使用Security 的认证管理器
   */
  @Autowired
  private AuthenticationManager authenticationManager;


  /**
   * Token 存储方式
   */
  @Autowired
  private TokenStore tokenStore;

  /**
   * jwt方式转换Token
   */
  @Autowired
  private AccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  private DataSource dataSource;


  /**
   * 数据库方式查询注册的客户端信息
   *
   * @return
   */
  @Bean
  public ClientDetailsService jdbcClientDetailsService() {
    return new JdbcClientDetailsService(dataSource);
  }

  /**
   * 授权服务器的安全配置信息
   *
   * @param security
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    // 认证后可访问 /oauth/check_token , 默认拒绝访问
    security.checkTokenAccess("isAuthenticated()");
    // 所有人可访问 /oauth/token_key 后面要获取公钥, 默认拒绝访问
    security.tokenKeyAccess("permitAll");

  }

  /**
   * 数据库方式获取注册的客户端信息
   *
   * @param clients
   * @throws Exception
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(jdbcClientDetailsService());
  }

  /**
   * @param endpoints
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // 密码模式时设置
    endpoints.authenticationManager(authenticationManager);
    //令牌方式获取UserDetail
    endpoints.userDetailsService(customUserDetailsService);
    // token存储方式设置
    endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);

  }
}
