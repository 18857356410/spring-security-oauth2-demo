package demo.common;

/**
 * <b><code>ResultCode</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/8 上午11:41.
 *
 * @author FS_425 WangZongYi
 * @since springboot-demo 1.0
 */
public enum ResultCode {


  /**
   * 操作成功
   */
  SUCCESS(200, "操作成功"),

  /**
   * 操作失败
   */
  FAILED(500, "操作失败"),

  /**
   * 参数检验失败
   */
  VALIDATE_FAILED(404, "参数检验失败"),

  /**
   * 暂未登录或token已经过期
   */
  UNAUTHORIZED(401, "暂未登录或token已经过期"),

  /**
   * 没有相关权限
   */
  FORBIDDEN(403, "没有相关权限");


  private long code;

  private String message;

  ResultCode(long code, String message) {
    this.code = code;
    this.message = message;
  }


  public long getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
