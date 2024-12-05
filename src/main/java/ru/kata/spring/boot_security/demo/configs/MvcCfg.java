package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcCfg implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth").setViewName("auth/login_bt");
        registry.addViewController("/admin").setViewName("admin/admin_page_bt");
        registry.addViewController("/user_bt").setViewName("user/user_bt");
    }
}

