package edu.seattleu.addressmanager.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.opentelemetry.api.metrics.LongHistogram;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.Instant;
import java.time.Duration;

@Component
public class TelemetryInterceptor  implements HandlerInterceptor {
    private final Timer requestDuration;
    private final Counter requestCounter;

    public TelemetryInterceptor(Timer requestDuration, Counter requestCounter) {
        this.requestDuration = requestDuration;
        this.requestCounter = requestCounter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("requestStartTime", Instant.now());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Instant startTime = (Instant) request.getAttribute("requestStartTime");
        if (startTime != null) {
            Duration duration = Duration.between(startTime, Instant.now());
            requestDuration.record(duration);
        }

        requestCounter.increment();
    }
}
