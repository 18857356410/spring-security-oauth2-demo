package demo.security;

import demo.security.service.CustomUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * <b><code>CustomizedAuthenticationProvider</code></b>
 * <p/>
 * 自定义通用安全组件OAuth2认证提供者
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 15:51.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class CustomizedAuthenticationProvider implements AuthenticationProvider {


  private CustomUserDetailService userDetailsService;

  public CustomUserDetailService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(CustomUserDetailService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    // 1、转换为自定义认证的Token
    CustomizedAuthenticationToken customizedAuthenticationToken = (CustomizedAuthenticationToken) authentication;

    // 2、获取用户信息,因为使用通用安全组件进行认证，实际已经包含了资源信息
    UserDetails userDetails = userDetailsService.loadUserByUsername(customizedAuthenticationToken);
    if (ObjectUtils.isEmpty(userDetails)) {
      throw new InternalAuthenticationServiceException("认证信息不合法或为空，认证失败！");
    }

    // 3、 如果有用户信息则认为认证通过
    CustomizedAuthenticationToken token = new CustomizedAuthenticationToken(userDetails.getUsername(), userDetails.getAuthorities());
    token.setDetails(customizedAuthenticationToken.getDetails());

    return token;
  }


  @Override
  public boolean supports(Class<?> authentication) {
    return false;
  }
}
