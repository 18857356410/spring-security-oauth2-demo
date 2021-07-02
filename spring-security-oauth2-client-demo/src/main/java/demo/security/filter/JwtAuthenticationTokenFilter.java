package demo.security.filter;

import cn.hutool.core.util.ObjectUtil;
import demo.dto.LoginOAuth2User;
import demo.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b><code>filter</code></b>
 * <p/>
 * Token 过滤器
 * <p/>
 * <b>Creation Time:</b> 2021/7/2 13:53.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Component("jwtAuthenticationTokenFilter")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    LoginOAuth2User loginOAuth2User = tokenService.getLoginUser(request);
    if (ObjectUtil.isNotNull(loginOAuth2User) &&
        ObjectUtil.isNull(SecurityContextHolder.getContext().getAuthentication())
    ) {
      // 验证用户是否有效
      tokenService.verifyToken(loginOAuth2User);

      // 缓存相应Token 完成根据Token 登录
      // OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken =
      //     new OAuth2LoginAuthenticationToken(clientRegistration,
      //     authorizationExchange,
      //     principal,
      //     accessToken,
      //     refreshToken);
      // OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken = new OAuth2LoginAuthenticationToken(null)
      // oAuth2LoginAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      // SecurityContextHolder.getContext().setAuthentication(oAuth2LoginAuthenticationToken);

    }

    filterChain.doFilter(request, response);
  }


}
