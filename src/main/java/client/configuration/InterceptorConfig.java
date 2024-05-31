package client.configuration;

import aspect.LoggingInterceptor;
import client.interceptor.CommonUserModelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static client.constants.PathConstants.HOME;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonUserModelInterceptor interceptor;
    private final LoggingInterceptor loggingInterceptor;
    @Autowired
    public InterceptorConfig(CommonUserModelInterceptor interceptor, LoggingInterceptor loggingInterceptor) {
        this.interceptor = interceptor;
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns(HOME); //path where it invokes

        registry.addInterceptor(loggingInterceptor)
                .order(Ordered.LOWEST_PRECEDENCE);
    }
}
