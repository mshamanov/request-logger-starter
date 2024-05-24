package com.mash.requestloggerstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration used to get user parameters for {@link LoggingRequestStarterAutoConfiguration}
 *
 * @author Mikhail Shamanov
 */
@Configuration
@ConfigurationProperties(prefix = "request-logger")
public class LoggingRequestStarterProperties {
    private boolean enabled;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}