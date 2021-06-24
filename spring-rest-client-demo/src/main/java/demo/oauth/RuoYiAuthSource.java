package demo.oauth;

import me.zhyd.oauth.config.AuthSource;

/**
 * <b><code>RuoyiAuthSource</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/24 16:14.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public enum RuoYiAuthSource implements AuthSource {


  /**
   * Ruoyi OAuth2
   */
  RUOYI {
    /**
     * 授权的api
     *
     * @return url
     */
    @Override
    public String authorize() {
      return "http://192.168.108.24:32038/oauth/authorize";
    }

    /**
     * 获取accessToken的api
     *
     * @return url
     */
    @Override
    public String accessToken() {
      return "http://192.168.108.24:32038/oauth/token";
    }

    /**
     * 获取用户信息的api
     *
     * @return url
     */
    @Override
    public String userInfo() {
      return "http://192.168.108.24:32038/oauth/check_token";
    }
  }
}
