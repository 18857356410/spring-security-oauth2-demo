package demo.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wangzongyi
 */
@RestController
@RequestMapping
public class RestDemoController {


  @GetMapping("/user")
  public ResponseEntity getUser(@RegisteredOAuth2AuthorizedClient("") OAuth2AuthorizedClient authorizedClient,
                                @AuthenticationPrincipal OAuth2User oauth2User) {

    HashMap<String, Object> stringStringHashMap = new HashMap<>();
    stringStringHashMap.put("userName", oauth2User.getName());
    stringStringHashMap.put("clientName", authorizedClient.getClientRegistration().getClientName());
    stringStringHashMap.put("userAttributes", oauth2User.getAttributes());

    return ResponseEntity.ok(stringStringHashMap);
  }


  @GetMapping("/test")
  @PreAuthorize("hasAuthority('system:list')")
  public ResponseEntity getUserList() {

    HashMap<String, Object> stringStringHashMap = new HashMap<>();
    ArrayList<String> users = new ArrayList<>();
    users.add("zhangsan");
    users.add("lisi");
    stringStringHashMap.put("users", users);

    return ResponseEntity.ok(stringStringHashMap);
  }


  /**
   * 返回认证的登录Token
   *
   * @return
   */
  @PostMapping("/token/")
  public ResponseEntity Oauth2Login() {


    return ResponseEntity.ok("");
  }


}
