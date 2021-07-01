package demo.security.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b><code>CustomizedForwardAuthenticationSuccessHandler</code></b>
 * <p/>
 * 自定义的认证成功转发处理器
 * <p/>
 * <b>Creation Time:</b> 2021/7/1 15:10.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class CustomizedAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Value("${customLogin.successUrl}")
  private String successUrl;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {

    String requestURI = request.getRequestURI();
    // if (!StringUtils.isEmpty(requestURI) && requestURI.startsWith("/login/oauth2/code/")) {
    //   response.sendRedirect(successUrl);
    // } else {
    //
    // }

    super.onAuthenticationSuccess(request, response, authentication);
  }
}
