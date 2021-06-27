package demo.security;

import demo.oauth.RuoYiAuthRequest;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b><code>CustonizedAuthenticationFilter</code></b>
 * <p/>
 * 自定义通用安全组件OAuth认证过滤器
 * <p/>
 * <b>Creation Time:</b> 2021/6/25 12:23.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class CustomizedAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  /**
   * 自定义的回调地址
   */
  public static String CUSTOMIZED_LOGIN_URL = "/oauth/render/ruoyi";

  private boolean postOnly = true;

  protected CustomizedAuthenticationFilter() {
    super(new AntPathRequestMatcher("CUSTOMIZED_LOGIN_URL", "POST"));
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

    // 验证是否是Post的请求登录
    if (postOnly && !request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    }

    // 这里有两种策略
    // case 1 这里只获取Token,在loadUserDetail 时候实现用户信息的获取
    // case 2 这里直接获取用户明细信息
    AuthCallback callback = getCallback(request);
    AuthUser authUser = obtainUser(callback);

    CustomizedAuthenticationToken token = new CustomizedAuthenticationToken(authUser);

    // 设置明细
    setDetails(request, token);

    // 认证管理器进行Token 认证
    return this.getAuthenticationManager().authenticate(token);
  }




  /**
   * 请求回调参数信息
   *
   * @param request
   * @return
   */
  protected AuthCallback getCallback(HttpServletRequest request) {
    AuthCallback authCallback = AuthCallback.builder()
        .code(request.getParameter("code"))
        .authorization_code(request.getParameter("authorization_code"))
        .state(request.getParameter("state"))
        .build();

    return authCallback;
  }


  /**
   * 根据回调函数获取用户信息
   *
   * @param callback
   * @return
   */
  protected AuthUser obtainUser(AuthCallback callback) {
    AuthRequest authRequest = new RuoYiAuthRequest(AuthConfig.builder()
        .clientId("ceshiOAuth")
        .clientSecret("admin")
        .redirectUri("http://localhost:8443/oauth/callback/ruoyi")
        .build());


    // 从通用安全组件获取用户信息
    AuthResponse authResponse = authRequest.login(callback);
    if (authResponse.getCode() == AuthResponseStatus.SUCCESS.getCode()) {
      return (AuthUser) authResponse.getData();
    }

    return null;
  }


  protected void setDetails(HttpServletRequest request,
                            CustomizedAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }
}
