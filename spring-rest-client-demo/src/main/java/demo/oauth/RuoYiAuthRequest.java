package demo.oauth;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.http.support.HttpHeader;
import demo.dto.AuthUserDto;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthDefaultRequest;
import me.zhyd.oauth.utils.Base64Utils;
import me.zhyd.oauth.utils.HttpUtils;
import me.zhyd.oauth.utils.UrlBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b><code>RuoYiAuthRequest</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/6/24 16:21.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
public class RuoYiAuthRequest extends AuthDefaultRequest {


  public RuoYiAuthRequest(AuthConfig config) {
    super(config, RuoYiAuthSource.RUOYI);
  }

  public RuoYiAuthRequest(AuthConfig config, AuthSource source, AuthStateCache authStateCache) {
    super(config, RuoYiAuthSource.RUOYI, authStateCache);
  }

  @Override
  protected AuthToken getAccessToken(AuthCallback authCallback) {
    String body = doPostAuthorizationCode(authCallback.getCode());
    JSONObject object = JSONObject.parseObject(body);

    this.checkResponse(object);

    return AuthToken.builder()
        .accessToken(object.getString("access_token"))
        .refreshToken(object.getString("refresh_token"))
        .idToken(object.getString("id_token"))
        .tokenType(object.getString("token_type"))
        .scope(object.getString("scope"))
        .build();
  }

  @Override
  protected AuthUser getUserInfo(AuthToken authToken) {
    String body = doGetUserInfo(authToken);
    JSONObject object = JSONObject.parseObject(body);

    this.checkResponse(object);
    Object[] authorities = object.getJSONArray("authorities").toArray();
    List<String> collect = new ArrayList<>();
    if (authorities.length > 0) {
      collect = Arrays.stream(authorities).map(Object::toString).collect(Collectors.toList());
    }

    AuthUserDto authUserDto = new AuthUserDto();
    authUserDto.setAuthorities(collect);
    authUserDto.setUsername(object.getString("user_name"));
    authUserDto.setClientId(object.getString("client_id"));
    return authUserDto;
  }

  /**
   * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
   *
   * @param state state 验证授权流程的参数，可以防止csrf
   * @return 返回授权地址
   * @since 1.11.0
   */
  @Override
  public String authorize(String state) {
    return UrlBuilder.fromBaseUrl(super.authorize(state))
        .queryParam("scope", "all")
        .build();
  }

  @Override
  protected String doGetUserInfo(AuthToken authToken) {

    HttpUtils httpUtils = new HttpUtils(config.getHttpConfig());
    String userInfoUrl = userInfoUrl(authToken);
    HttpHeader httpHeader = new HttpHeader();
    String encode = "Basic " + Base64Utils.encode("ceshiOAuth:admin".getBytes());
    httpHeader.add("Authorization", encode);
    httpUtils.get(userInfoUrl, null, httpHeader, false);

    return httpUtils.post(userInfoUrl, null, httpHeader, false);
  }

  /**
   * 返回获取userInfo的url
   *
   * @param authToken token
   * @return 返回获取userInfo的url
   */
  @Override
  protected String userInfoUrl(AuthToken authToken) {
    return UrlBuilder.fromBaseUrl(source.userInfo()).queryParam("token", authToken.getAccessToken()).build();
  }

  private void checkResponse(JSONObject object) {
    // oauth/token 验证异常
    if (object.containsKey("error")) {
      throw new AuthException(object.getString("error_description"));
    }
    // user 验证异常
    if (object.containsKey("message")) {
      throw new AuthException(object.getString("message"));
    }
  }


}
