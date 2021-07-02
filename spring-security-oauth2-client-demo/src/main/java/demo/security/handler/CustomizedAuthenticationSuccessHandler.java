package demo.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

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
@Slf4j
public class CustomizedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Value("${customLogin.successUrl}")
  private String successUrl;

  private RequestCache requestCache = new HttpSessionRequestCache();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {



    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest == null) {

      // request.getRequestDispatcher("/user").forward(request, response);
      getRedirectStrategy().sendRedirect(request, response, successUrl);
      return;
    }

    clearAuthenticationAttributes(request);
    String targetUrl = savedRequest.getRedirectUrl();
    logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }


}
