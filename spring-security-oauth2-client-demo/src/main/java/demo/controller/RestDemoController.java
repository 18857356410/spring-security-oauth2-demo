package demo.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import demo.dto.Auth2User;
import demo.dto.LoginOAuth2User;
import demo.security.TokenService;
import lombok.extern.slf4j.Slf4j;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangzongyi
 */
@RestController
@RequestMapping
@Slf4j
public class RestDemoController {


  @Autowired
  private TokenService tokenService;

  /**
   * OAuth2 登录后获取Token
   *
   * @param authorizedClient
   * @param oauth2User
   * @return
   */
  @GetMapping("/token")
  public ResponseEntity getUser(@RegisteredOAuth2AuthorizedClient("client3") OAuth2AuthorizedClient authorizedClient,
                                @AuthenticationPrincipal OAuth2User oauth2User) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    oauth2User.getAuthorities().forEach(n -> {
          if (n instanceof OAuth2UserAuthority) {
            Map<String, Object> attributes = ((OAuth2UserAuthority) n).getAttributes();
            Auth2User auth2User = JSON.parseObject(new JSONObject(attributes).toJSONString(), Auth2User.class);
            Set<SimpleGrantedAuthority> collect = auth2User.getAuthorities().stream().distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
            authorities.addAll(collect);
          } else if (n instanceof SimpleGrantedAuthority) {
            authorities.add(n);
          } else {
            log.info("OAuth2 登录的用户没有简单授权类型的角色可以新增");
          }
        }
    );


    Instant expiresAt = authorizedClient.getAccessToken().getExpiresAt();
    long expireTime = ChronoUnit.SECONDS.between(Instant.now(), expiresAt) * 60;

    LoginOAuth2User loginOAuth2User = LoginOAuth2User.builder()
        .attributes(oauth2User.getAttributes())
        .authorities(authorities)
        .authorizedClientRegistrationId(authorizedClient.getClientRegistration().getRegistrationId())
        .expireTime(expireTime)
        .loginTime(Instant.now())
        .nameAttributeKey("userName")
        .uuidString(IdUtil.fastUUID())
        .build();


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
