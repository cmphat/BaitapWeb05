package vn.iotstar.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteMeshConfig {

    @Bean
    public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter() {
        FilterRegistrationBean<ConfigurableSiteMeshFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ConfigurableSiteMeshFilter() {
            @Override
            protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
                builder.setDecoratorPrefix("/decorators/");
                builder.addExcludedPath("/css/*");
                builder.addExcludedPath("/js/*");
                builder.addExcludedPath("/images/*");
                builder.addExcludedPath("/assets/*");
                builder.addExcludedPath("/uploads/*");
                builder.addExcludedPath("/decorators/*");
                builder.setDispatchMode(org.sitemesh.webapp.DispatchMode.INCLUDE);
                builder.addDecoratorPath("/*", "main.jsp");
            }
        });
        filter.setDispatcherTypes(java.util.EnumSet.of(jakarta.servlet.DispatcherType.FORWARD));
        filter.addUrlPatterns("/*");
        filter.setName("sitemeshFilter");
        filter.setOrder(1);
        return filter;
    }
}
