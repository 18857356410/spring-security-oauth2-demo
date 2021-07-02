SpringSecurityOAuth2  接入通用安全组件Demo

项目说明

```
SpringSecurityOAuth2Demo 主要是提供如何通过使用SpringSecurity相关技术计入通用安全组件的资源角色权限Demo，
 包含通过 资源服务器方式、OAuth2Client 方式 、前后端分离方式、不分离方式、如何接入SSO功能提供简单的代码示例
```

##### 主要技术

| Spring Security        | [https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/index.html]() |
| :--------------------- | ------------------------------------------------------------ |
| Spring Security  CAS   | [https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/index.html]() |
| Spring Security OAuth2 | [https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/index.html]() |
| Resource Server        | [https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/index.html]() |
| OAuth2  Server         | [https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/index.html]() |
| SSO                    | [https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/index.html]() |







##### 项目初始化

```
1   新建数据库spring-boot-demo

2  初始化客户端明细表及数据，即执行spring-security-oauth-demo\spring-sso-server-demo\src\main\resources\db 目录下的sql 文件

3 启动ServerApplication成功后再启动其他应用
```



##### 项目介绍

```zh
1. spring-security-oauth2-demo
    1. spring-security-oauth2-client-demo  使用OAuth2-client 接入通用安全组件认证服务器
    2. spring-resource-client-demo  作为资源服务器接入通用安全组件认证服务器
    3. spring-sso-cas  springsecurity 接入CAS
    4. spring-sso-client1-demo   SSO 接入client1
    5. spring-sso-client2-demo   SSO 接入client2
    6. spring-sso-server-demo    认证服务端
```

