package demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author wangzongyi
 */
@RestController
@RequestMapping
public class RestDemoController {

  private final RedirectStrategy authorizationRedirectStrategy = new DefaultRedirectStrategy();


  @GetMapping("/user")
  public ResponseEntity getUser(@RegisteredOAuth2AuthorizedClient("client3") OAuth2AuthorizedClient authorizedClient,
                                @AuthenticationPrincipal OAuth2User oauth2User) {

    HashMap<String, Object> stringStringHashMap = new HashMap<>();
    stringStringHashMap.put("userName", oauth2User.getName());
    stringStringHashMap.put("clientName", authorizedClient.getClientRegistration().getClientName());
    stringStringHashMap.put("userAttributes", oauth2User.getAttributes());
    stringStringHashMap.put("accessToken", authorizedClient.getAccessToken());
    stringStringHashMap.put("refreshToken", authorizedClient.getRefreshToken());
    stringStringHashMap.put("redirectUri", authorizedClient.getClientRegistration().getRedirectUriTemplate());

    return ResponseEntity.ok(stringStringHashMap);
  }


  @GetMapping("/zhangsan")
  @PreAuthorize("hasAuthority('system')")
  public ResponseEntity getUserList() {

    HashMap<String, Object> stringStringHashMap = new HashMap<>();

    stringStringHashMap.put("name", "zhangsan");
    stringStringHashMap.put("sex", "女");


    return ResponseEntity.ok(stringStringHashMap);
  }


  /**
   * 返回认证的登录Token
   *
   * @return
   */
  @GetMapping("/wangwu")
  public ResponseEntity Oauth2Login() {

    HashMap<String, Object> stringStringHashMap = new HashMap<>();

    stringStringHashMap.put("name", "王五");
    stringStringHashMap.put("sex", "男");

    return ResponseEntity.ok(stringStringHashMap);
  }

  /**
   * 返回认证的登录Token
   *
   * @return
   */
  @PostMapping("/error")
  public ResponseEntity error() {


    return ResponseEntity.ok("认证失败");
  }

}
