package demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * <b><code>CasUserDetailService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/5/31 18:15.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Component
@Slf4j
public class CasUserDetailService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {


  @Override
  public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {

    // 获取
    log.info("登录的用户信息:{}", token.getPrincipal());

    //由于登录交于cas服务端管理，如果进入这里代表登录成功，可不做任何操作，也可以根据自身系统业务开发相关功能代码
    return new User(token.getName(),
        "",
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

  }
}
