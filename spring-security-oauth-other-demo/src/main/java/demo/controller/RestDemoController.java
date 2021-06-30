package demo.controller;

import demo.common.ServiceData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangzongyi
 */
@RestController
@RequestMapping
public class RestDemoController {


  @GetMapping(value = "/")
  @PreAuthorize("hasAuthority('system:string')")
  public ServiceData<String> getString() {


    return ServiceData.success("123");
  }











}
