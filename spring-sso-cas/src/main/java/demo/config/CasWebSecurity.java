package demo.config;


import demo.properties.CasProperties;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * <b><code>CasWebSecurity</code></b>
 * <p/>
 * CAS web security 配置类
 * <p/>
 * <b>Creation Time:</b> 2021/5/31 18:20.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CasWebSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  private CasProperties casProperties;

  @Autowired
  private AuthenticationUserDetailsService casUserDetailsService;

  @Autowired
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(casAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 放行OPTIONS请求
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .anyRequest().authenticated();
    http
        // 注销放行
        .logout().permitAll()
        // 表单登录
        .and().formLogin();

    http
        .exceptionHandling()
        // 设置CAS 认证入口
        .authenticationEntryPoint(casAuthenticationEntryPoint())
        .and()
        // CAS 过滤器
        .addFilter(casAuthenticationFilter())
        .addFilterBefore(logoutFilter(), LogoutFilter.class)
        .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);

    http.csrf().disable();

  }


  @Bean
  public CasAuthenticationProvider casAuthenticationProvider() {
    CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();

    // 设置用户登录成功后会加载用户信息
    casAuthenticationProvider.setAuthenticationUserDetailsService(casUserDetailsService);
    // 设置客户端信息
    casAuthenticationProvider.setServiceProperties(serviceProperties());
    // 设置CAS 票据验证
    casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
    // 设置CAS 秘钥
    casAuthenticationProvider.setKey(casProperties.getKeySecurity());

    return casAuthenticationProvider;
  }

  @Bean
  public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
    return new Cas20ServiceTicketValidator(casProperties.getServerUrlPrefix());
  }

  @Bean
  public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
    CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();

    //设置CAS 登录URL
    casAuthenticationEntryPoint.setLoginUrl(casProperties.getServerLoginUrl());

    // 设置cas客户端信息
    casAuthenticationEntryPoint.setServiceProperties(serviceProperties());

    return casAuthenticationEntryPoint;

  }


  @Bean
  public ServiceProperties serviceProperties() {
    ServiceProperties serviceProperties = new ServiceProperties();

    // 设置客户端登录的完整URL
    serviceProperties.setService(casProperties.getHostUrl() + casProperties.getLoginUrl());
    serviceProperties.setAuthenticateAllArtifacts(true);

    return serviceProperties;
  }

  @Bean
  public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
    CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();

    // 设置认证管理器
    casAuthenticationFilter.setAuthenticationManager(authenticationManager());
    casAuthenticationFilter.setFilterProcessesUrl(casProperties.getHostUrl() + casProperties.getLoginUrl());
    casAuthenticationFilter.setServiceProperties(serviceProperties());

    return casAuthenticationFilter;
  }

  @Bean
  public LogoutFilter logoutFilter() {
    LogoutFilter logoutFilter = new LogoutFilter(casProperties.getServerLogoutUrl(), new SecurityContextLogoutHandler());
    logoutFilter.setFilterProcessesUrl(casProperties.getLogoutUrl());

    return logoutFilter;
  }

  @Bean
  public SingleSignOutFilter singleSignOutFilter() {
    SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
    singleSignOutFilter.setCasServerUrlPrefix(casProperties.getServerUrlPrefix());
    singleSignOutFilter.setIgnoreInitConfiguration(true);

    return singleSignOutFilter;
  }

}
