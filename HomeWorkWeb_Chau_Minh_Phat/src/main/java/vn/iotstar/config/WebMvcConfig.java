package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import vn.iotstar.interceptor.AdminSecurityInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AdminSecurityInterceptor adminSecurityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminSecurityInterceptor)
                .addPathPatterns("/admin", "/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/uploads/**").addResourceLocations("/uploads/");
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.web.server.WebServerFactoryCustomizer<org.springframework.boot.web.server.servlet.ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            java.io.File docBase = new java.io.File("src/main/webapp");
            if (!docBase.exists()) {
                docBase = new java.io.File("target/Exercise");
            }
            if (docBase.exists()) {
                factory.setDocumentRoot(docBase);
            }
        };
    }
}
