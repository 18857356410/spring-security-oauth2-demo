package demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.zhyd.oauth.model.AuthUser;

import java.util.List;

/**
 * <b><code>CustomizedAuthUser</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/24 17:35.
 *
 * @author FS_425 WangZongYi
 * @since justauth-demo 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDto extends AuthUser {

  /**
   * 资源标识
   */
  private List<String> authorities;


  /**
   * 注册的客户端
   */
  private String clientId;


}
