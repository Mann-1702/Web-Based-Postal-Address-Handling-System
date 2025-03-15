package edu.seattleu.addressmanager.config;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class TelemetryConfig {
    @Bean
    public Timer requestDurationHistogram(MeterRegistry registry) {
        return Timer.builder("http.server.duration")
                .description("Address API request duration")
                .publishPercentileHistogram()
                .register(registry);
    }

    @Bean
    public Counter requestCounter(MeterRegistry registry) {
        return Counter.builder("http.server.requests")
                .description("Total Address API requests")
                .register(registry);
    }

}
