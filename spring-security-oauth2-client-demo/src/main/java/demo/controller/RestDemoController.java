package demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import demo.dto.LoginOAuth2User;
import demo.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangzongyi
 */
@RestController
@RequestMapping
public class RestDemoController {


  @Autowired
  private TokenService tokenService;

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

    LoginOAuth2User loginOAuth2User = new LoginOAuth2User();
    loginOAuth2User.setAttributes(oauth2User.getAttributes());

    JSONObject jsonObject = new JSONObject(oauth2User.getAttributes());


    OAuth2UserAuthority oAuth2UserAuthority = (OAuth2UserAuthority) oauth2User.getAuthorities();


    loginOAuth2User.setNameAttributeKey(oauth2User.getName());
    loginOAuth2User.setExpireTime(System.currentTimeMillis() + (30 * 60 * 60));
    loginOAuth2User.setLoginTime(System.currentTimeMillis());
    loginOAuth2User.setUuidString(IdUtil.fastUUID());
    loginOAuth2User.setOauth2Authority(oAuth2UserAuthority.getAuthority());
    loginOAuth2User.setOAuth2Attributes(oAuth2UserAuthority.getAttributes());

    String token = tokenService.createToken(loginOAuth2User);

    return ResponseEntity.ok(token);
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
