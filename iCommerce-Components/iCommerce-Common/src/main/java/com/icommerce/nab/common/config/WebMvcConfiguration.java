package com.icommerce.nab.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.icommerce.nab.common.constant.ICommerceConstants.DATE_FORMAT;

/**
 * Configuration for de-serializing json body
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
            }
        }
    }
}