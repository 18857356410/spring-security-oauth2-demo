package demo.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * @author wangzongyi
 */
@Data
@ToString
public class Auth2User implements Serializable {

  /**
   * 消息文本
   */
  private String msg;

  /**
   * 消息码
   */
  private String code;


  /**
   * 手机号码
   */
  private String phoneNumber;


  /**
   * 客户端Id
   */
  private String clientId;


  /**
   * 授权范围
   */
  private Set<String> scope;

  /**
   * 是否有效
   */
  private String active;

  /**
   * 用户名称
   */
  private String userName;

  /**
   * 权限范围
   */
  private Set<String> authorities;


}
