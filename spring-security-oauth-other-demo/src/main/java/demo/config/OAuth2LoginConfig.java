package demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.ArrayList;

/**
 * @author wangzongyi
 */
@Configuration
public class OAuth2LoginConfig {


  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {

    ArrayList<ClientRegistration> clientRegistrationArrayList = new ArrayList<>();
    clientRegistrationArrayList.add(this.githubClientRegistration());
    clientRegistrationArrayList.add(this.canYanClientRegistration());

    return new InMemoryClientRegistrationRepository(clientRegistrationArrayList);
  }

  private ClientRegistration githubClientRegistration() {
    return ClientRegistration.withRegistrationId("github")
        .clientId("d5e863e1a4ea47e2216f")
        .clientSecret("18da614dde5e90425708403f0172220028eb63f4")
        .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
        .scope("all")
        .authorizationUri("https://github.com/login/oauth/authorize")
        .tokenUri("https://github.com/login/oauth/access_token")
        .userInfoUri("https://api.github.com/user")
        .userNameAttributeName("login")
        .clientName("GitHub 登录")
        .build();
  }

  private ClientRegistration canYanClientRegistration() {
    return ClientRegistration.withRegistrationId("client3")
        .clientId("client3")
        .clientSecret("admin")
        .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
        // .scope("all")
        .authorizationUri("http://auth-server:9095/oauth/authorize")
        .tokenUri("http://auth-server:9095/oauth/token")
        .userInfoUri("http://auth-server:9095/oauth2/user")
        // .userInfoUri("http://auth-server:9095/getInfo")
        .userNameAttributeName("userName")
        .clientName("广东省移动产研通用安全组件登录")
        .build();
  }
}
