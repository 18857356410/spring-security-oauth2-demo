package demo.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b><code>CustomizedForwardAuthenticationFailureHandler</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/7/1 15:13.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class CustomizedAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    super.onAuthenticationFailure(request, response, exception);
  }
}
