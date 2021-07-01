package demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author wangzongyi
 */
@Configuration
@EnableWebSecurity
public class ServerWebSecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  private UserDetailsService customUserDetailsService;

  /**
   * 使用OauthServer 配置时候需要使用的认证管理器
   *
   * @return
   * @throws Exception
   */
  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  /**
   * 配置使用数据库方式查询用户信息
   *
   * @param auth
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
  }
}
