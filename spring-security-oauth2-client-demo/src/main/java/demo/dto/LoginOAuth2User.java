package demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

/**
 * <b><code>CustomizedOAuth2User</code></b>
 * <p/>
 * 自定义的OAuth2.0 用户信息
 * <p/>
 * <b>Creation Time:</b> 2021/7/2 17:25.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Data
@ToString
@Builder
public class LoginOAuth2User implements Serializable {

  private static final long serialVersionUID = 2432647029825257482L;


  public LoginOAuth2User() {
  }

  public LoginOAuth2User(DefaultOAuth2User defaultOAuth2User, Collection<? extends GrantedAuthority> authorities, String authorizedClientRegistrationId, Map<String, Object> attributes, String nameAttributeKey, Long expireTime, Instant loginTime, String uuidString) {
    this.defaultOAuth2User = defaultOAuth2User;
    this.authorities = authorities;
    this.authorizedClientRegistrationId = authorizedClientRegistrationId;
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.expireTime = expireTime;
    this.loginTime = loginTime;
    this.uuidString = uuidString;
  }

  /**
   * 默认的OAuth2 登录的用户信息
   */
  private DefaultOAuth2User defaultOAuth2User;


  /**
   * OAuth2 的用户授权信息
   */
  private Collection<? extends GrantedAuthority> authorities;


  /**
   * 授权注册的客户端ID
   */
  private String authorizedClientRegistrationId;


  /**
   * 其他属性
   */
  private Map<String, Object> attributes;


  /**
   * 登录名称字段
   */
  private String nameAttributeKey;


  /**
   * 过期时间
   */
  private Long expireTime;


  /**
   * 登录时间
   */
  private Instant loginTime;


  /**
   * 唯一id
   */
  private String uuidString;


}
