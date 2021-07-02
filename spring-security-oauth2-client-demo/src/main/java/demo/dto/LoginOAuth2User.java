package demo.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.List;
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
public class LoginOAuth2User implements Serializable {

  private static final long serialVersionUID = 2432647029825257482L;


  private List<SimpleGrantedAuthority> authorities;

  /**
   * 权限名称
   */
  private String oauth2Authority;

  /**
   * 权限属性
   */
  private Map<String, Object> OAuth2Attributes;


  private Map<String, Map<String, Object>> oauth2AttributesList;
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
  private Long loginTime;


  /**
   * 唯一id
   */
  private String uuidString;


}
