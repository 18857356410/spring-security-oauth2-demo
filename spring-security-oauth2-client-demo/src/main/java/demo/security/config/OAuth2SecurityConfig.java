package demo.security.config;

import demo.security.handler.CustomizedAccessDeniedHandler;
import demo.security.handler.CustomizedAuthenticationFailureHandler;
import demo.security.handler.CustomizedAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzongyi
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {


  /**
   * 未授权的处理器
   */
  @Autowired
  private CustomizedAccessDeniedHandler customizedAccessDeniedHandler;


  @Bean
  public CustomizedAuthenticationSuccessHandler customizedAuthenticationSuccessHandler() {

    return new CustomizedAuthenticationSuccessHandler();
  }

  @Bean
  CustomizedAuthenticationFailureHandler customizedAuthenticationFailureHandler() {

    return new CustomizedAuthenticationFailureHandler();
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
    // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    // http.authorizeRequests()
    //     .antMatchers("/login", "/oauth2/authorization/**").permitAll()
    //     .anyRequest().authenticated();


    http.exceptionHandling()
        // 设置认证入口
        // .authenticationEntryPoint(customizedAuthenticationEntryPoint)
        // 未授权返回
        .accessDeniedHandler(new CustomizedAccessDeniedHandler());

    // OAuth2Client 授权登录端点
    // http.oauth2Login()
    //     .loginPage("/login/oauth2")
    //     .authorizationEndpoint().baseUri("/login/oauth2/authorization");



    //  获取用户信息端点配置
    http.oauth2Login().userInfoEndpoint()
        //  将授权的用户信息存储到
        .userAuthoritiesMapper(this.userAuthoritiesMapper());

    // 自定义成功处理器和失败处理器
    // http.oauth2Login()
    //     .successHandler(customizedAuthenticationSuccessHandler())
    //     .failureHandler(customizedAuthenticationFailureHandler())
    ;
    //自定义的OAuth2 客户端注册
    // .oauth2Client(oauth2Client -> oauth2Client
    //     .clientRegistrationRepository(this.clientRegistrationRepository()))


  }


  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/favicon*");
  }

  /**
   * 使用OAuth2 的授权
   *
   * @return
   */
  private GrantedAuthoritiesMapper userAuthoritiesMapper() {

    GrantedAuthoritiesMapper grantedAuthoritiesMapper = (authorities) -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach(authority -> {
        if (OAuth2UserAuthority.class.isInstance(authority)) {
          OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;

          // 获取OAuth2 认证用户的授权信息
          Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
          // 如果用户信息包含相应的授权信息则添加到现有授权中
          if (userAttributes != null && userAttributes.get("authorities") != null) {
            List<SimpleGrantedAuthority> oauthAuthorities =
                Arrays.asList(userAttributes.get("authorities"))
                    .stream().map(n -> new SimpleGrantedAuthority(n.toString()))
                    .collect(Collectors.toList());

            mappedAuthorities.addAll(oauthAuthorities);
          }
        }
      });

      return mappedAuthorities;
    };

    return grantedAuthoritiesMapper;
  }


  /**
   * 直接使用 AccessToken 获取用户信息
   *
   * @return
   */
  private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {

    // 标准的OAuth2 默认实现
    // final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    final OidcUserService delegate = new OidcUserService();

    OAuth2UserService<OidcUserRequest, OidcUser> oidcUserRequestOidcUserOAuth2UserService = (userRequest) -> {
      // 委托给加载用户的默认实现
      OidcUser oidcUser = delegate.loadUser(userRequest);

      OAuth2AccessToken accessToken = userRequest.getAccessToken();
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      // TODO
      // 1) 使用 accessToken 从受保护资源中获取权限信息
      // 2) 将权限信息映射到一个或多个 GrantedAuthority 并将其添加到 mappingAuthorities
      // 3)创建 oidcUser 的副本，但使用 mappingAuthorities 代替
      oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

      return oidcUser;
    };

    return oidcUserRequestOidcUserOAuth2UserService;
  }

}
