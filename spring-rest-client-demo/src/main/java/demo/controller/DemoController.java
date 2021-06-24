package demo.controller;

import com.alibaba.fastjson.JSONObject;
import demo.oauth.RuoYiAuthRequest;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b><code>DemoController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/24 15:13.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class DemoController {


  /**
   * 重定向到登录界面
   *
   * @param source
   * @param response
   * @throws IOException
   */
  @RequestMapping("/render/{source}")
  @ResponseBody
  public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
    log.info("进入render：" + source);
    AuthRequest authRequest = getAuthRequest();
    String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
    log.info(authorizeUrl);

    response.sendRedirect(authorizeUrl);
  }


  /**
   * 注册的回调地址
   *
   * @param source
   * @param callback
   * @param request
   * @return
   */
  @GetMapping("/callback/{source}")
  public AuthResponse<AuthUser> login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request) {
    log.info("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
    AuthRequest authRequest = getAuthRequest();
    AuthResponse<AuthUser> response = authRequest.login(callback);

    log.info(JSONObject.toJSONString(response));

    return response;
  }


  @GetMapping(value = "/info")
  @PreAuthorize("hasAuthority('sys:index')")
  public String getInfo() {

    return "123";
  }

  /**
   * 获取客户端请求信息
   *
   * @return
   */
  private AuthRequest getAuthRequest() {
    return new RuoYiAuthRequest(AuthConfig.builder()
        .clientId("ceshiOAuth")
        .clientSecret("admin")
        .redirectUri("http://localhost:9003/oauth/callback/ruoyi")
        .build());
  }




}
