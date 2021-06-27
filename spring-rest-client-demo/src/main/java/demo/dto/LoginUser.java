package demo.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * <b><code>LoginUser</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 17:17.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class LoginUser implements UserDetails {

  /**
   * 用户权限
   */
  private List<SimpleGrantedAuthority> authority;

  /**
   * 用户名称
   */
  private String userName;


  public List<SimpleGrantedAuthority> getAuthority() {
    return authority;
  }

  public void setAuthority(List<SimpleGrantedAuthority> authority) {
    this.authority = authority;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authority;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


}
