package demo.common;

/**
 * <b><code>ErrorCode</code></b>
 * <p/>
 * 错误编码接口
 * <p/>
 * <b>Creation Time:</b> 2021/3/8 上午11:38.
 *
 * @author FS_425 WangZongYi
 * @since springboot-demo 1.0
 */
public interface ErrorCode {

  /**
   * 获取编码
   *
   * @return
   */
  long getCode();

  /**
   * 获取错误信息
   *
   * @return
   */
  String getMessage();

}
