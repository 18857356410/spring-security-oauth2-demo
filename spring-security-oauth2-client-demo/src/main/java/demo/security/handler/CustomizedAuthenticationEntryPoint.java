// package demo.security.handler;
//
// import com.alibaba.fastjson.JSON;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.stereotype.Component;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.HashMap;
//
//
// /**
//  * 自定义的认证入口
//  *
//  * @author wangzongyi
//  */
// @Component("customizedAuthenticationEntryPoint")
// public class CustomizedAuthenticationEntryPoint implements AuthenticationEntryPoint {
//   @Override
//   public void commence(HttpServletRequest request,
//                        HttpServletResponse response,
//                        AuthenticationException authException) throws IOException, ServletException {
//
//     HashMap<String, Object> returnMap = new HashMap<>();
//     if (authException instanceof BadCredentialsException) {
//       returnMap.put("code", HttpStatus.UNAUTHORIZED);
//       returnMap.put("msg", "用户未认证");
//     }
//
//     response.setContentType("application/json;charset=UTF-8");
//     response.getWriter().write(JSON.toJSONString(returnMap));
//   }
// }
