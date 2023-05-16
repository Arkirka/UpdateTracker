package ru.tinkoff.edu.java.bot.metric;

import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;

@Component
@RequiredArgsConstructor
public class MetricProcessor {
    private final ApplicationConfig applicationConfig;

    public void incrementMessageCount() {
        Metrics.counter(applicationConfig.metricsProcessedMessageCountName()).increment();
    }
}
