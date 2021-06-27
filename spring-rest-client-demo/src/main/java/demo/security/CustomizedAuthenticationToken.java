package demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <b><code>CustomizedAuthToken</code></b>
 * <p/>
 * 自定义通用安全组件认证Token
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 12:19.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class CustomizedAuthenticationToken extends AbstractAuthenticationToken {

  /**
   * 存储从第三方获取的用户信息
   */
  private final Object principal;


  public CustomizedAuthenticationToken(Object principal) {
    super(null);
    this.principal = principal;
    super.setAuthenticated(false);
  }


  public CustomizedAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }
}
