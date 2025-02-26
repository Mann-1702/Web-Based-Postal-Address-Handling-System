package edu.seattleu.addressmanager.metrics;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import org.springframework.stereotype.Component;

/**
 * This Class used to collect  Java instrumentation and metric
 */
@Component
public class CustomMetrics {
    private final LongCounter addressCounter;

    public CustomMetrics() {
        // Acquire a global Meter
        Meter meter = GlobalOpenTelemetry.getMeter("edu.seattleu.addressmanager");
        // Create a custom counter metric
        this.addressCounter = meter
                .counterBuilder("address_operations_counter")
                .setDescription("Counts how many address operations occur")
                .setUnit("operations")
                .build();
    }

    public void incrementAddressOps() {
        addressCounter.add(1);
    }



}
