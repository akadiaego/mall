package com.hxr.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置类
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserArgumentResolver userArgumentResolver;
//    @Autowired
//    private AccessLimitInterceptor accessLimitInterceptor;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //添加解析器以支持自定义控制器方法参数类型。
        //用在对于Controller中方法参数传入之前对该参数进行处理。然后将处理好的参数在传给Controller中的方法
        resolvers.add(userArgumentResolver);

    }
    //静态资源展示
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置类大于配置文件
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //swagger 和 knife4j
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(accessLimitInterceptor);
//    }

}
