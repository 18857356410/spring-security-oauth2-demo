package demo.controller;

import demo.common.ServiceData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>ProductController</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2021/3/22 11:01.
 *
 * @author FS_425 WangZongYi
 * @since mengxuegu-cloud-oauth2-parent 1.0
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {


  @GetMapping(value = "/list")
  @PreAuthorize("hasAuthority('sys:index')")
  public ServiceData<List<String>> getList() {
    List<String> productList = new ArrayList<>();
    productList.add("眼睛");
    productList.add("双肩包");
    productList.add("格子寸衫");

    return ServiceData.success(productList);

  }


}
