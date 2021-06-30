package demo.config;

import demo.security.CustomizedAuthenticationEntryPoint;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wangzongyi
 */

@EnableWebSecurity
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {


  private CustomizedAuthenticationEntryPoint customizedAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated()
        .and()
        .exceptionHandling()
        // 设置认证入口
        .authenticationEntryPoint(customizedAuthenticationEntryPoint)
        .and()
        // OAuth 认证方式
        .oauth2Login(oauth2login ->
            // 默认的登录界面
            oauth2login.loginPage("/oauth2/login")
                // 自定义认证的url
                .authorizationEndpoint().baseUri("/login/oauth2/code/*")
                .and()
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userAuthoritiesMapper(this.userAuthoritiesMapper()))

        )
        //自定义的OAuth2 客户端注册
        // .oauth2Client(oauth2Client -> oauth2Client
        //     .clientRegistrationRepository(this.clientRegistrationRepository()))

        .logout()
        .logoutSuccessUrl("http://192.168.108.24:32038/logout");

  }

  /**
   * 使用OAuth2 的授权
   *
   * @return
   */
  private GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return (authorities) -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach(authority -> {
        if (OidcUserAuthority.class.isInstance(authority)) {
          OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

          OidcIdToken idToken = oidcUserAuthority.getIdToken();
          OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

          // Map the claims found in idToken and/or userInfo
          // to one or more GrantedAuthority's and add it to mappedAuthorities

        } else if (OAuth2UserAuthority.class.isInstance(authority)) {
          OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;

          Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

          // Map the attributes found in userAttributes
          // to one or more GrantedAuthority's and add it to mappedAuthorities

        }
      });

      return mappedAuthorities;
    };
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
