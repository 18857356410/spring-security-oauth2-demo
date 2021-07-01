package demo.security.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import demo.dto.Auth2User;
import demo.security.handler.CustomizedAccessDeniedHandler;
import demo.security.handler.CustomizedAuthenticationFailureHandler;
import demo.security.handler.CustomizedAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    // 未授权返回 json
    http.exceptionHandling().accessDeniedHandler(customizedAccessDeniedHandler);

    // 可自定义 OAuth2Client 授权登录端点
    // http.oauth2Login()
    //     .loginPage("/login/oauth2")
    //     .authorizationEndpoint().baseUri("/login/oauth2/authorization");


    //  获取用户信息端点配置
    http.oauth2Login().userInfoEndpoint()
        //  获取通用安全组件的授权信息
        .userAuthoritiesMapper(this.userAuthoritiesMapper());

    // 自定义成功处理器和失败处理器
    http.oauth2Login()
        .successHandler(customizedAuthenticationSuccessHandler())
        .failureHandler(customizedAuthenticationFailureHandler());

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
            String jsonObject = new JSONObject(userAttributes).toString();
            Auth2User auth2User = JSON.parseObject(jsonObject, Auth2User.class);
            List<SimpleGrantedAuthority> collect = auth2User.getAuthorities().stream().distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

            mappedAuthorities.addAll(collect);
          }
        }
      });

      return mappedAuthorities;
    };

    return grantedAuthoritiesMapper;
  }

}