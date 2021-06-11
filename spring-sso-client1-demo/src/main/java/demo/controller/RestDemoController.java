package demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangzongyi
 */
@RestController
@RequestMapping(value = "/user")
public class RestDemoController {


  @GetMapping(value = "/info")
  @PreAuthorize("hasAuthority('sys:index')")
  public String getInfo() {

    return "123";
  }



}
