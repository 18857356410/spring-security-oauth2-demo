package demo.security.service.impl;

import demo.dto.AuthUserDto;
import demo.dto.LoginUser;
import demo.security.CustomizedAuthenticationToken;
import demo.security.service.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <b><code>CustomizedUserDetailServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 17:12.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Service
@Slf4j
public class CustomizedUserDetailServiceImpl implements CustomUserDetailService {


  @Override
  public UserDetails loadUserByUsername(CustomizedAuthenticationToken token) throws UsernameNotFoundException {
    log.info("获取到的用户信息是：{}", token.getPrincipal());

    if (null == token.getPrincipal()) {
      throw new UsernameNotFoundException("用户未在通用安全组件进行注册，请联系管理员进行注册");
    }

    // todo  将OAuth2 服务端的Token解码为自己的用户信息

    LoginUser loginUser = new LoginUser();
    if (token.getPrincipal() instanceof AuthUserDto) {
      AuthUserDto authUserDto = (AuthUserDto) token.getPrincipal();

      loginUser.setUserName(authUserDto.getUsername());
      List<SimpleGrantedAuthority> collect = authUserDto
          .getAuthorities()
          .stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());

      loginUser.setAuthority(collect);
    }

    return loginUser;
  }
}
