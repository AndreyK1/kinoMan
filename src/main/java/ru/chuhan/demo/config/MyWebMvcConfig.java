package ru.chuhan.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.chuhan.demo.mapper.DeepFieldFilter;


@Configuration
public class MyWebMvcConfig {

//security
//    https://habr.com/ru/post/482552/

    @Bean
    public WebMvcConfigurer forwardToIndex() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html

                registry.addViewController("/admin").setViewName(
                        "forward:/admin/index.html");
//                registry.addViewController("/").setViewName(
//                        "forward:/common/index.html");

                registry.addViewController("/").setViewName(
                        "forward:/common/show.html");
                registry.addViewController("/login").setViewName(
                        "forward:/login.html");
                registry.addViewController("/registration").setViewName(
                        "forward:/registration.html");

            }

//            @Bean
//            public ViewResolver viewResolver() {
//                InternalResourceViewResolver bean = new InternalResourceViewResolver();
//
//                bean.setViewClass(JstlView.class);
////                bean.setPrefix("/WEB-INF/view/");
//                bean.setPrefix("/WEB-INF/jsp/");
//                bean.setSuffix(".jsp");
//
//                return bean;
//            }
        };
    }


    @Bean
    @Primary
    public ObjectMapper customJson() {
                SimpleFilterProvider depthFilters = new SimpleFilterProvider().addFilter("depth_1", new DeepFieldFilter(1))
                .addFilter("depth_2", new DeepFieldFilter(2))
                .addFilter("depth_3", new DeepFieldFilter(3))
//                        .addFilter("depth_4", new DeepFieldFilter(4))
                        .addFilter("depth_5", new DeepFieldFilter(5))
                        .addFilter("depth_7", new DeepFieldFilter(7))
                .addFilter("depth_15", new DeepFieldFilter(15));

        return new Jackson2ObjectMapperBuilder()
                .filters(depthFilters)
//                .setFilterProvider(depthFilters);

                .build();
    }

}
