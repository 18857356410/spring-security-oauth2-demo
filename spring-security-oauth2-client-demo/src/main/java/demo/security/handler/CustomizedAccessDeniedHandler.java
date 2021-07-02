package demo.security.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * <b><code>CustomizedNoAccessDeniedHandler</code></b>
 * <p/>
 * 自定义的没有权限访问处理器
 * <p/>
 * <b>Creation Time:</b> 2021/7/1 14:51.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Component("customizedAccessDeniedHandler")
public class CustomizedAccessDeniedHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {


  @Override
  public void handle(HttpServletRequest request,
                     HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {

    HashMap<String, Object> returnMap = new HashMap<>();
    returnMap.put("code", HttpStatus.FORBIDDEN);
    returnMap.put("msg", "用户没有权限，请找管理员授权");
    response.setContentType("application/json;charset=UTF-8");

    response.getWriter().write(JSON.toJSONString(returnMap));
  }

}
