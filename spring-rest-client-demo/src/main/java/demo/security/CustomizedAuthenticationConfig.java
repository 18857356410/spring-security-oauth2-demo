package demo.security;

import demo.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * <b><code>CustomizedAuthenticationProvdler</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 15:30.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Component("customizedAuthenticationConfig")
public class CustomizedAuthenticationConfig
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


  /**
   * 接入人员可选择自定义
   */
  @Autowired
  private AuthenticationSuccessHandler successHandler;

  /**
   * 接入人员可选择自定义
   */
  @Autowired
  private AuthenticationFailureHandler failureHandler;


  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception {

    // 1、初始化自定义认证过滤器
    CustomizedAuthenticationFilter filter = new CustomizedAuthenticationFilter();
    // 获取容器中已经存在的AuthenticationManager对象，并传入 mobileAuthenticationFilter 里面
    filter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
    filter.setAuthenticationSuccessHandler(successHandler);
    filter.setAuthenticationFailureHandler(failureHandler);

    //2、初始化自定义的Provider
    CustomizedAuthenticationProvider provider = new CustomizedAuthenticationProvider();
    provider.setUserDetailsService(customUserDetailService);

    //3、将设置的Filter 和Provider 添加到过滤链中
    httpSecurity.authenticationProvider(provider)
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
