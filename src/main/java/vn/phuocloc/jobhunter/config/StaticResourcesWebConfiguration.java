package vn.phuocloc.jobhunter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    @Value("${phuocloc.upload-file.base-uri}")
    private String baseURI;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ở đây có nghĩa là mỗi lần mình vào /storage/** thì spring sẽ tìm trong
        // basepath
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(baseURI);

    }

}
