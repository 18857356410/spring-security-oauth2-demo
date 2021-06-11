package demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * <b><code>ResourceServerConfig</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/22 11:08.
 *
 * @author FS_425 WangZongYi
 * @since mengxuegu-cloud-oauth2-parent
 */
@Configuration
@EnableResourceServer //标注资源管理服务器
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法器注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  /**
   * 资源服务器ID
   */
  private static final String RESOURCE_ID = "product-server";

  @Autowired
  private TokenStore tokenStore;


  /**
   * 当前资源服务器的一些配置，如资源服务器ID、token
   *
   * @param resources
   * @throws Exception
   */
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    // 配置当前资源的服务器的ID，会在认证服务器上验证（客户端表的resource上是否配置了就可以访问）
    resources.resourceId(RESOURCE_ID)
        .tokenStore(tokenStore);
  }

  /**
   * 指定访问的资源
   *
   * @param http
   * @throws Exception
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        // SpringSecurity不会创建也不会使用HttpSession
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // 资源授权规则
        //.antMatchers("/product/**").hasAuthority("all")
        //所有请求都需要有all范围（scope）
        .anyRequest()
        .access("#oauth2.hasScope('all')");
  }
}
