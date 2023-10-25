package ru.ds.magnitfaqchatbot.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.ds.magnitfaqchatbot.property.RestTemplateProperties;

import java.util.Arrays;
import java.util.Collections;

import static ru.ds.magnitfaqchatbot.config.CommonConfiguration.*;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestConfiguration {

    public static final String DEFAULT_AUTH_USER_REST_TEMPLATE_BEAN = "authUserRestTemplateBean";

    RestTemplateProperties restTemplateProperties;


    /**
     * Rest template for auth user service with common interceptor and message converter
     */
    @Bean(name = DEFAULT_AUTH_USER_REST_TEMPLATE_BEAN)
    public RestTemplate userInfoRestTemplate(
            @Qualifier(DEFAULT_HTTP_LOGGING_INTERCEPTOR_BEAN) ClientHttpRequestInterceptor httpLoggingInterceptor,
            @Qualifier(DEFAULT_JACKSON_2_HTTP_CONVERTER) MappingJackson2HttpMessageConverter messageConverter
    ) {
        return restTemplateForService(
                restTemplateProperties.getReadTimeout(),
                restTemplateProperties.getAuthUserServiceName(),
                Collections.singletonList(httpLoggingInterceptor),
                Collections.singletonList(messageConverter));
    }
}
