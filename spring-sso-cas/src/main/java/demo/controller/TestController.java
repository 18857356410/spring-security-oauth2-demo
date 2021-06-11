package demo.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * <b><code>TestController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/5/31 19:11.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@RestController
@RequestMapping
@Slf4j
public class TestController {


  @SneakyThrows
  @GetMapping(value = "/login")
  public String login(HttpServletRequest request, HttpServletResponse response) {
    //只是为了跳转到cas服务端的登录页面  登录成功后会跳回此页
    //可根据自身系统进行代码增强
    // AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
    Principal userPrincipal = request.getUserPrincipal();
    String name = userPrincipal.getName();

    return name + "登录成功了";
  }

  @GetMapping(value = "/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    //只是为了跳转到cas服务端的登出页面
    String service = request.getParameter("service");
    log.info(service);
    //可根据自身系统进行代码增强
    // response.sendRedirect();
  }

  @GetMapping(value = "/hello")
  public String hello(HttpServletRequest request) {

    Principal userPrincipal = request.getUserPrincipal();
    String name = userPrincipal.getName();
    return "hello word";
  }


}
