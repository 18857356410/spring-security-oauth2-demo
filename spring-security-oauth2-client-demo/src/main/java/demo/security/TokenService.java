package demo.security;

import cn.hutool.core.util.IdUtil;
import demo.common.config.RedisCache;
import demo.dto.LoginOAuth2User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <b><code>TokenService</code></b>
 * <p/>
 * 将登录的用户信息生成为Token
 * <p/>
 * <b>Creation Time:</b> 2021/7/2 13:55.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Component("tokenService")
public class TokenService {


  @Value("${token.header}")
  private String header;


  @Value("${token.secret}")
  private String secret;

  @Value("${token.expireTime}")
  private int expireTime;

  @Value("${token.intervalTime}")
  private Long intervalTime;

  @Value("${token.loginKey}")
  private String loginKey;


  @Autowired
  private RedisCache redisCache;


  /**
   * 创建Token并将用户信息存储在redis中
   *
   * @param loginOAuth2User
   * @return
   */
  public String createToken(LoginOAuth2User loginOAuth2User) {
    String uuid = IdUtil.fastUUID();
    loginOAuth2User.setUuidString(uuid);

    refreshToken(loginOAuth2User);

    HashMap<String, Object> claims = new HashMap<>();
    claims.put(loginKey, uuid);

    String token = Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, secret).compact();

    return token;
  }


  /**
   * 刷新Token
   *
   * @param loginOAuth2User
   * @return
   */
  public void refreshToken(LoginOAuth2User loginOAuth2User) {
    loginOAuth2User.setLoginTime(Instant.now());

    //自定义失效时间
    loginOAuth2User.setExpireTime((long) expireTime);

    redisCache.setCacheObject(loginOAuth2User.getUuidString(),
        loginOAuth2User,
        loginOAuth2User.getExpireTime().intValue(),
        TimeUnit.MINUTES);
  }


  /**
   * 校验Token 间隔时间不超过20分钟，自动刷新token
   *
   * @param loginOAuth2User 登录用户
   */
  public void verifyToken(LoginOAuth2User loginOAuth2User) {
    long expireTime = loginOAuth2User.getExpireTime();
    long currentTime = System.currentTimeMillis();
    if (expireTime - currentTime <= intervalTime) {
      refreshToken(loginOAuth2User);
    }
  }


  /**
   * 从令牌中获取数据声明
   *
   * @param token 令牌
   * @return 数据声明
   */
  public Claims parseToken(String token) {
    return Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
  }


  /**
   * 获取登录的用户
   *
   * @param request
   * @return
   */
  public LoginOAuth2User getLoginUser(HttpServletRequest request) {
    String token = request.getHeader(this.header);

    String startToken = "Bearer ";
    if (!StringUtils.isEmpty(token) && token.startsWith(startToken)) {
      String accessToken = token.replace(startToken, "");
      Claims claims = parseToken(accessToken);
      String uuid = (String) claims.get(loginKey);
      LoginOAuth2User loginUser = null;
      try {
        loginUser = (LoginOAuth2User) redisCache.getCacheObject(uuid);
      } catch (Exception e) {
        e.printStackTrace();
      }

      return loginUser;
    }

    return null;
  }


}
