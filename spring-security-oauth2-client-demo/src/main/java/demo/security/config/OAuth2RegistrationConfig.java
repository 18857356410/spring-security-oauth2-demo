package demo.security.config;


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
public class OAuth2RegistrationConfig {


  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {

    ArrayList<ClientRegistration> clientRegistrationArrayList = new ArrayList<>();
    clientRegistrationArrayList.add(this.canYanClientRegistration());

    return new InMemoryClientRegistrationRepository(clientRegistrationArrayList);
  }


  private ClientRegistration canYanClientRegistration() {
    return ClientRegistration.withRegistrationId("client3")
        .clientId("client3")
        .clientSecret("admin")
        .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
        // .redirectUriTemplate("{baseUrl}/login/demo/string/{registrationId}")
        // .scope("all")
        .authorizationUri("http://192.168.108.24:32038/oauth/authorize")
        .tokenUri("http://192.168.108.24:32038/oauth/token")
        .userInfoUri("http://192.168.108.24:32038/oauth2/user")
        // .userInfoUri("http://192.168.108.24:32038/getInfo")
        .userNameAttributeName("userName")
        .clientName("广东省移动产研通用安全组件登录")
        .build();
  }
}
