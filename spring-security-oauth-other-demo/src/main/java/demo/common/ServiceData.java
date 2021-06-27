package demo.common;

/**
 * <b><code>ServiceData</code></b>
 * <p/>
 * 服务返回结果统一封装
 * <p/>
 * <b>Creation Time:</b> 2021/3/8 上午11:44.
 *
 * @author FS_425 WangZongYi
 * @since springboot-demo 1.0
 */
public class ServiceData<T> {

  /**
   * 返回前端的编码
   */
  private long resultCode;
  /**
   * 返回前端的错误信息
   */
  private String resultMessage;
  /**
   * 返回前端的业务实体
   */
  private T resultObject;

  public ServiceData() {

  }

  /**
   * 成功返回结果
   *
   * @param data 获取的数据
   */
  public static <T> ServiceData<T> success(T data) {
    return new ServiceData<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
  }

  /**
   * 成功返回结果
   *
   * @param data    获取的数据
   * @param message 提示信息
   */
  public static <T> ServiceData<T> success(T data, String message) {
    return new ServiceData<T>(ResultCode.SUCCESS.getCode(), message, data);
  }

  /**
   * 失败返回结果
   *
   * @param errorCode 错误码
   */
  public static <T> ServiceData<T> failed(ErrorCode errorCode) {
    return new ServiceData<T>(errorCode.getCode(), errorCode.getMessage(), null);
  }

  /**
   * 失败返回结果
   *
   * @param message 提示信息
   */
  public static <T> ServiceData<T> failed(String message) {
    return new ServiceData<T>(ResultCode.FAILED.getCode(), message, null);
  }

  /**
   * 失败返回结果
   */
  public static <T> ServiceData<T> failed() {
    return failed(ResultCode.FAILED.getMessage());
  }

  /**
   * 参数验证失败返回结果
   */
  public static <T> ServiceData<T> validateFailed() {
    return failed(ResultCode.VALIDATE_FAILED.getMessage());
  }

  /**
   * 参数验证失败返回结果
   *
   * @param message 提示信息
   */
  public static <T> ServiceData<T> validateFailed(String message) {
    return new ServiceData<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
  }

  /**
   * 未登录返回结果
   */
  public static <T> ServiceData<T> unauthorized(T data) {
    return new ServiceData<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
  }

  /**
   * 未授权返回结果
   */
  public static <T> ServiceData<T> forbidden(T data) {
    return new ServiceData<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
  }

  protected ServiceData(long resultCode, String resultMessage, T resultObject) {
    this.resultCode = resultCode;
    this.resultMessage = resultMessage;
    this.resultObject = resultObject;
  }

  public long getResultCode() {
    return resultCode;
  }

  public void setResultCode(long resultCode) {
    this.resultCode = resultCode;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public void setResultMessage(String resultMessage) {
    this.resultMessage = resultMessage;
  }

  public T getResultObject() {
    return resultObject;
  }

  public void setResultObject(T resultObject) {
    this.resultObject = resultObject;
  }


}
