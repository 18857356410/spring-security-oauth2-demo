package demo.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <b><code>RedisConfig</code></b>
 * <p/>
 * Redis 配置类
 * <p/>
 * <b>Creation Time:</b> 2021/7/2 14:50.
 *
 * @author FS_425 WangZongYi
 * @since spring-security-oauth-demo 1.0
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {


  @Bean
  @SuppressWarnings(value = {"unchecked", "rawtypes"})
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    serializer.setObjectMapper(mapper);

    template.setValueSerializer(serializer);
    // 使用StringRedisSerializer来序列化和反序列化redis的key值
    template.setKeySerializer(new StringRedisSerializer());
    template.afterPropertiesSet();

    return template;

  }

}
