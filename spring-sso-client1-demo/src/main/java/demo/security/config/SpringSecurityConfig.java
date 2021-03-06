package demo.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author wangzongyi
 */
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.oauth2.logout-uri}")
  private String logoutUri;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 首页和登录界面都跳过认证
        .antMatchers("/", "/login**").permitAll()
        //其他资源都需要认证后才能访问。
        .anyRequest().authenticated()
        .and()
        .logout()
        //当应用退出后交给远端处理,请求认证服务器将应用退出
        .logoutSuccessUrl(logoutUri)
        .and()
        //暂时屏蔽csrf过滤
        .csrf().disable();
  }


}
