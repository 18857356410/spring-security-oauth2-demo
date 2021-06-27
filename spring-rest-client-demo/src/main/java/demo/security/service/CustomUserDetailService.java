package demo.security.service;

import demo.security.CustomizedAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <b><code>CustomUserDetailService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 17:13.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public interface CustomUserDetailService  {

  /**
   * 加载用户信息
   *
   * @param token 自定义的Token
   * @return
   * @throws UsernameNotFoundException
   */
  UserDetails loadUserByUsername(CustomizedAuthenticationToken token) throws UsernameNotFoundException;
}
