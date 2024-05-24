package com.mash.requestloggerstarter.config;

import com.mash.requestloggerstarter.filter.LoggingRequestFilter;
import com.mash.requestloggerstarter.filter.logger.LoggingRequestHandler;
import com.mash.requestloggerstarter.filter.logger.CommonLoggingRequestHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * AutoConfiguration settings for the starter to get loaded if required.
 *
 * @author Mikhail Shamanov
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingRequestStarterProperties.class)
@ConditionalOnProperty(prefix = "request-logger", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggingRequestStarterAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LoggingRequestHandler loggingRequestHandler() {
        return new CommonLoggingRequestHandler();
    }

    @Bean
    public LoggingRequestFilter loggingRequestFilter(LoggingRequestHandler loggingRequestHandler) {
        return new LoggingRequestFilter(loggingRequestHandler);
    }
}
