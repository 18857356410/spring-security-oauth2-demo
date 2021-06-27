package demo.security.config;

import demo.security.CustomizedAuthenticationConfig;
import demo.security.CustomizedAuthenticationProvider;
import demo.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <b><code>CustomWebSecurityConfig</code></b>
 * <p/>
 * SpringSecurity 配置类
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 17:48.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomizedAuthenticationConfig customizedAuthenticationConfig;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.authenticationProvider(customizedAuthenticationProvider);
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 取消CSRF
    http.csrf().disable();
    // 基于token，所以不需要session
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        // 跨域预检请求
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // 忽略登录
        .antMatchers("/oauth/render/*", "/oauth/callback/**").permitAll()
        // 其它请求需要认证
        .anyRequest().authenticated();

    http.headers().frameOptions().disable();
    http.formLogin()
        .loginProcessingUrl("/oauth/render/ruoyi");
    http.apply(customizedAuthenticationConfig);

  }


  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
